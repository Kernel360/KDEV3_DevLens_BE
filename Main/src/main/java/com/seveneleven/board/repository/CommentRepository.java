package com.seveneleven.board.repository;

import com.seveneleven.entity.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT COALESCE(MAX(c.ref), 0) FROM Comment c")
    Long findMaxRef();

    @Query("""
            SELECT c
            FROM Comment c
            WHERE c.postId.id = :postId AND c.isActive = 'YES'
            ORDER BY c.ref ASC, c.refOrder ASC
            """)
    List<Comment> getCommentList(@Param("postId") Long postId);
}
