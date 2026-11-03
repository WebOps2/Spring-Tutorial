package com.example.demo.exception;

import java.util.UUID;

public class ProjectNotFoundException extends RuntimeException {

    private final UUID id;

    public ProjectNotFoundException(UUID id) {
        super(String.format("Project with id %s not found", id));
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}