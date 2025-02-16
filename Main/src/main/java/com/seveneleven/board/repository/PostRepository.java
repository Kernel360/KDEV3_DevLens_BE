package com.seveneleven.board.repository;

import com.seveneleven.board.dto.PostListResponse;
import com.seveneleven.board.dto.RelatedPostResponse;
import com.seveneleven.entity.board.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // REF의 최대값 구하기
    @Query("SELECT COALESCE(MAX(p.ref), 0) FROM Post p")
    Long findMaxRef();

    // 부모게시글의 REF 구하기
    Optional<Long> findRefById(Long parentPostId);

    // 그룹 내 refOrder 최대값 구하기
    @Query("SELECT MAX(p.refOrder) FROM Post p WHERE p.parentPost.id = :parentPostId")
    Optional<Integer> findMaxRefOrderByParentPostId(@Param("parentPostId") Long parentPostId);

    // 자식 게시글 id와 title 조회
    @Query("""
    SELECT new com.seveneleven.board.dto.RelatedPostResponse(p.id, p.title)
    FROM Post p WHERE p.parentPost.id = :postId
    """)
    List<RelatedPostResponse> findChildPostIdsAndTitlesByParentId(@Param("postId") Long postId);

    // 전체 게시글 목록 조회
    @Query("""
    SELECT new com.seveneleven.board.dto.PostListResponse(
        p,
        COUNT(c)
        )
    FROM Post p
    LEFT JOIN Comment c
        ON c.post.id = p.id
    WHERE
        p.projectStep.id IN (:projectStepIds)
    AND (
        (:filter = 'TITLE' AND p.title LIKE CONCAT('%', :keyword, '%')) OR
        (:filter = 'CONTENT' AND p.content LIKE CONCAT('%', :keyword, '%')) OR
        (:filter = 'WRITER' AND p.writer LIKE CONCAT('%', :keyword, '%')) OR
        ((:filter IS NULL OR :filter = 'ALL') AND
            (:keyword IS NULL OR p.title LIKE CONCAT('%', :keyword, '%') OR
             p.content LIKE CONCAT('%', :keyword, '%') OR
             p.writer LIKE CONCAT('%', :keyword, '%'))
        )
    )
    GROUP BY p.id
    """)
    Page<PostListResponse> findAllPosts(@Param("projectStepIds") List<Long> projectStepIds,
                                        @Param("keyword") String keyword,
                                        @Param("filter") String filter,
                                        Pageable pageable);

    // 프로젝트 단계별 게시글 목록 조회
    @Query("""
    SELECT new com.seveneleven.board.dto.PostListResponse(
        p,
        COUNT(c)
        )
    FROM Post p
    LEFT JOIN Comment c
        ON c.post.id = p.id
    WHERE p.projectStep.id = :projectStepId
    AND (
        (:filter = 'TITLE' AND p.title LIKE CONCAT('%', :keyword, '%')) OR
        (:filter = 'CONTENT' AND p.content LIKE CONCAT('%', :keyword, '%')) OR
        (:filter = 'WRITER' AND p.writer LIKE CONCAT('%', :keyword, '%')) OR
        ((:filter IS NULL OR :filter = 'ALL') AND
            (:keyword IS NULL OR p.title LIKE CONCAT('%', :keyword, '%') OR
             p.content LIKE CONCAT('%', :keyword, '%') OR
             p.writer LIKE CONCAT('%', :keyword, '%'))
        )
    )
    GROUP BY p.id
    """)
    Page<PostListResponse> findAllByProjectStepId(@Param("projectStepId") Long projectStepId,
                                      @Param("keyword") String keyword,
                                      @Param("filter") String filter,
                                      Pageable pageable);
}