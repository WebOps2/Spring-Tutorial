package com.example.demo.exception;

import java.util.UUID;

public class CommentNotFoundException extends RuntimeException {
    private final UUID id;
    public CommentNotFoundException(UUID id) {
        super("Comment not found");
        this.id = id;
        
    }
    public UUID getId() {
        return id;
    }
}
