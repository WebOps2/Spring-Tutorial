package com.example.demo.domain;

import java.util.UUID;

public record CreateCommentRequest(
    String author,
    String content,
    UUID taskId
) {
}
