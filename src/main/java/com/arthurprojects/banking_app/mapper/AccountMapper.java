package com.arthurprojects.banking_app.mapper;

import com.arthurprojects.banking_app.dto.AccountDto;
import com.arthurprojects.banking_app.entity.Account;
import lombok.Data;

public class AccountMapper {
    public static Account mapToAccount(AccountDto accountDto){
        //converting AccountDto to account
        Account account = new Account(accountDto.getId(), accountDto.getAccountHolderName(), accountDto.getBalance());
        return account;
    }
    //converting Account Jpa entity to dto
    public static AccountDto mapToAccountDto(Account account){
        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getAccountHolderName()
        );
        return accountDto;
    }

}
