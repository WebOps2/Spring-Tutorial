package com.example.demo.Mapper.impl;

import org.springframework.stereotype.Component;

import com.example.demo.Mapper.CommentMapper;
import com.example.demo.domain.CreateCommentRequest;
import com.example.demo.domain.UpdateCommentRequest;
import com.example.demo.domain.dto.CommentDto;
import com.example.demo.domain.dto.CreateCommentRequestDto;
import com.example.demo.domain.dto.UpdateCommentRequestDto;
import com.example.demo.domain.entity.Comments;

@Component
public class CommentMapperImpl implements CommentMapper {
    @Override
    public CommentDto toDto(Comments comment) {
        return new CommentDto(comment.getId(), comment.getCreatedAt(), comment.getUpdatedAt(), comment.getAuthor(), comment.getContent(), comment.getTask().getId());
    }

    @Override
    public CreateCommentRequest fromDto(CreateCommentRequestDto dto) {
        return new CreateCommentRequest(dto.author(), dto.content(), dto.taskId());
    }

    @Override
    public UpdateCommentRequest fromDto(UpdateCommentRequestDto dto) {
        return new UpdateCommentRequest(dto.content());
    }
}
