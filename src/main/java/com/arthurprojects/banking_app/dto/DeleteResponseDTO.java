package com.arthurprojects.banking_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class DeleteResponseDTO {
    private String message;
    private LocalDateTime timestamp;
    private UUID id;

    public static UUID generateUUID() {
        return UUID.randomUUID();
    }
}
