package com.example.demo.Mapper;

import com.example.demo.domain.CreateCommentRequest;
import com.example.demo.domain.UpdateCommentRequest;
import com.example.demo.domain.dto.CommentDto;
import com.example.demo.domain.dto.CreateCommentRequestDto;
import com.example.demo.domain.dto.UpdateCommentRequestDto;
import com.example.demo.domain.entity.Comments;

public interface CommentMapper {
    CommentDto toDto(Comments comment);
    CreateCommentRequest fromDto(CreateCommentRequestDto createCommentRequestDto);
    UpdateCommentRequest fromDto(UpdateCommentRequestDto updateCommentRequestDto);
}
