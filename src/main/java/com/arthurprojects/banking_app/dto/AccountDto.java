package com.arthurprojects.banking_app.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String accountName;
    private double balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
