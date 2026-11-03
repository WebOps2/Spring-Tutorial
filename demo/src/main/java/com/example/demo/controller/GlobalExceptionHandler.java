package com.example.demo.controller;

import java.util.UUID;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.domain.dto.ErrorDto;
import com.example.demo.exception.CommentNotFoundException;
import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.exception.TaskNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
        .findFirst()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .orElse("Validation error");

        ErrorDto errorDto = new ErrorDto(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorDto> handleTaskNotFoundException(TaskNotFoundException ex) {
       UUID id = ex.getId();
       String errorMessage = String.format("Task with id %s not found", id);
       ErrorDto errorDto = new ErrorDto(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProjectNotFoundException(ProjectNotFoundException ex) {
        UUID id = ex.getId();
        String errorMessage = String.format("Project with id %s not found", id);
        ErrorDto errorDto = new ErrorDto(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleUnreadableMessage(HttpMessageNotReadableException ex) {
        ErrorDto errorDto = new ErrorDto(
            "Invalid request body. Priority must be LOW, MEDIUM or HIGH, "
            + "status must be OPEN or COMPLETE, and dates must use the format yyyy-MM-dd.");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCommentNotFoundException(CommentNotFoundException ex) {
        UUID id = ex.getId();
        String errorMessage = String.format("Comment with id %s not found", id);
        ErrorDto errorDto = new ErrorDto(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

}