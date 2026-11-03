package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.domain.CreateCommentRequest;
import com.example.demo.domain.UpdateCommentRequest;
import com.example.demo.domain.entity.Comments;

@Service
public interface CommentService {
    Comments createComment(CreateCommentRequest request);
    Comments updateComment(UUID commentId, UpdateCommentRequest request);
    void deleteComment(UUID commentId);
    Comments getCommentById(UUID commentId);
    List<Comments> getCommentsByTaskId(UUID taskId);
}
