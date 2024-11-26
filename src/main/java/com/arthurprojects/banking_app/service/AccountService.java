package com.arthurprojects.banking_app.service;

import com.arthurprojects.banking_app.dto.AccountDto;
import com.arthurprojects.banking_app.dto.CreateAccountRequest;
import com.arthurprojects.banking_app.dto.WithdrawalRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    AccountDto createAccount(CreateAccountRequest request);
    AccountDto getAccountById(Long id);
    AccountDto deposit(Long id, Double amount);
    AccountDto withdraw(Long id, WithdrawalRequest request);
    Page<AccountDto> getAllAccounts(Pageable pageable);
}
