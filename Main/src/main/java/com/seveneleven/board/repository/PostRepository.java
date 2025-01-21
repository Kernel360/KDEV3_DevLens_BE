package com.seveneleven.board.repository;

import com.seveneleven.entity.board.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // REF의 최대값 구하기
    @Query("SELECT COALESCE(MAX(p.ref), 0) FROM Post p")
    Long findMaxRef();
    Long findFirstRefByOrderByRefDesc();

    // 부모게시글의 REF 구하기
    Optional<Long> findRefById(Long parentPostId);

    // 그룹 내 refOrder 최대값 구하기
    @Query("SELECT MAX(p.refOrder) FROM Post p WHERE p.parentPost.id = :parentPostId")
    Optional<Integer> findMaxRefOrderByParentPostId(@Param("parentPostId") Long parentPostId);

    Page<Post> findAllByProjectStepId(@Param("projectStepId") Long projectStepId, Pageable pageable);

}
