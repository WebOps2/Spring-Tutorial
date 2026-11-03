package com.example.demo.domain.dto;
import java.time.LocalDate;

import com.example.demo.domain.entity.TaskPriority;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreateTaskRequestDto(
    @NotBlank(message = ERROR_MESSAGE_TITLE_LENGTH)
    @Size(max = 255, message = ERROR_MESSAGE_TITLE_LENGTH)
    String title, 
    @Size(max = 1000, message = ERROR_MESSAGE_DESCRIPTION_LENGTH)
    @Nullable
    String description, 
    @Future(message = ERROR_MESSAGE_DUE_DATE_PAST)
    @Nullable 
    LocalDate dueDate, 
    @NotNull(message = ERROR_MESSAGE_PRIORITY_NULL)
    TaskPriority priority) {

        private static final String ERROR_MESSAGE_TITLE_LENGTH = "Title must be less than 255 characters";
        private static final String ERROR_MESSAGE_DESCRIPTION_LENGTH = "Description must be less than 1000 characters";
        private static final String ERROR_MESSAGE_DUE_DATE_PAST = "Due date must be in the future";
        private static final String ERROR_MESSAGE_PRIORITY_NULL = "Priority is required";

}
