package com.arthurprojects.banking_app.service;

import com.arthurprojects.banking_app.dto.AccountDto;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long id);

    AccountDto deposit(Long id, double amount);
}
