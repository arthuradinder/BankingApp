package com.arthurprojects.banking_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CreateAccountRequest {
    @NotBlank(message = "Account name is required")
    private String accountName;

    @NotNull(message = "Initial balance is required")
    @PositiveOrZero(message ="Balance must be zero or positive" )
    private Double balance;
}
