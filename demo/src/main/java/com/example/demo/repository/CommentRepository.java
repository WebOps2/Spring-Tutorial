package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.entity.Comments;

@Repository
public interface CommentRepository extends JpaRepository<Comments, UUID> {
    Optional<Comments> findById(UUID id);
    @Query("SELECT c FROM Comments c WHERE c.task.id = :taskId")
    List<Comments> findByTaskId(@Param("taskId") UUID taskId);
}
