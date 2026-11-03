package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Mapper.CommentMapper;
import com.example.demo.domain.CreateCommentRequest;
import com.example.demo.domain.UpdateCommentRequest;
import com.example.demo.domain.dto.CommentDto;
import com.example.demo.domain.dto.CreateCommentRequestDto;
import com.example.demo.domain.dto.UpdateCommentRequestDto;
import com.example.demo.domain.entity.Comments;
import com.example.demo.service.CommentService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CreateCommentRequestDto createCommentRequestDto) {
        CreateCommentRequest createCommentRequest = commentMapper.fromDto(createCommentRequestDto);
        Comments comment = commentService.createComment(createCommentRequest);
        CommentDto createdCommentDto = commentMapper.toDto(comment);
        return new ResponseEntity<>(createdCommentDto, HttpStatus.CREATED);
    }

    @GetMapping("/{taskId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByTaskId(@PathVariable UUID taskId) {
        List<Comments> comments = commentService.getCommentsByTaskId(taskId);
        List<CommentDto> commentDtos = comments.stream().map(commentMapper::toDto).toList();
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable UUID commentId, @Valid @RequestBody UpdateCommentRequestDto updateCommentRequestDto) {
        UpdateCommentRequest updateCommentRequest = commentMapper.fromDto(updateCommentRequestDto);
        Comments comment = commentService.updateComment(commentId, updateCommentRequest);
        CommentDto updatedCommentDto = commentMapper.toDto(comment);
        return new ResponseEntity<>(updatedCommentDto, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
