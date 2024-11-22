package com.arthurprojects.banking_app.service.impl;

import com.arthurprojects.banking_app.dto.AccountDto;
import com.arthurprojects.banking_app.entity.Account;
import com.arthurprojects.banking_app.mapper.AccountMapper;
import com.arthurprojects.banking_app.repository.AccountRepository;
import com.arthurprojects.banking_app.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService
{
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account saveAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(saveAccount);
    }

    @Override
    public AccountDto getAccountById (Long id){
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() ->new RuntimeException("Account does not exist"));
                return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() ->new RuntimeException("Account does not exist"));
        double total = account.getBalance()+amount;
        account.setBalance(total);
        Account saveAccount = accountRepository.save(account); //saving to db

        return AccountMapper.mapToAccountDto(saveAccount);
    }
}
