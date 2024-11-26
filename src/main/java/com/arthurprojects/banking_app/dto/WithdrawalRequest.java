package com.arthurprojects.banking_app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class WithdrawalRequest {
    @NotNull(message = "Withdrawal amount required")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;
}
