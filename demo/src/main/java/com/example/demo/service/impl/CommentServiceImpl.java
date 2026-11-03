package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.domain.CreateCommentRequest;
import com.example.demo.domain.UpdateCommentRequest;
import com.example.demo.domain.entity.Comments;
import com.example.demo.exception.CommentNotFoundException;
import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.CommentService;

@Service

public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    public CommentServiceImpl(CommentRepository commentRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Comments createComment(CreateCommentRequest request) {
        Comments comment = new Comments(
            null,
            LocalDateTime.now(),
            LocalDateTime.now(),
            request.author(),
            request.content(),
            taskRepository.findById(request.taskId()).orElseThrow(() -> new TaskNotFoundException(request.taskId()))
        );
        return commentRepository.save(comment);
    }

    @Override
    public Comments updateComment(UUID commentId, UpdateCommentRequest request) {
        Comments comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        comment.setContent(request.content());
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(UUID commentId) {
        commentRepository.deleteById(commentId);

    }

    @Override
    public Comments getCommentById(UUID commentId) {
        return null;
    }

    @Override
    public List<Comments> getCommentsByTaskId(UUID taskId) {
        return commentRepository.findByTaskId(taskId);
    }
}
