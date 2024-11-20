package com.arthurprojects.banking_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AccountDto {
    private Long id;
    private String accountHolderName;
    private double balance;

}
