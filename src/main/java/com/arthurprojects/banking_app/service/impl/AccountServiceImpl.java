package com.arthurprojects.banking_app.service.impl;

import com.arthurprojects.banking_app.dto.AccountDto;
import com.arthurprojects.banking_app.dto.CreateAccountRequest;
import com.arthurprojects.banking_app.dto.DeleteResponseDTO;
import com.arthurprojects.banking_app.dto.WithdrawalRequest;
import com.arthurprojects.banking_app.entity.Account;
import com.arthurprojects.banking_app.exception.InsufficientBalanceException;
import com.arthurprojects.banking_app.exception.ResourceNotFoundException;
import com.arthurprojects.banking_app.repository.AccountRepository;
import com.arthurprojects.banking_app.service.AccountService;
import com.arthurprojects.banking_app.util.SequenceManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final double WITHDRAWAL_CHARGE = 50.00;

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private SequenceManager sequenceManager;

    @Override
    public AccountDto createAccount(CreateAccountRequest request) {
        Account account = new Account();
        account.setAccountName(request.getAccountName());
        account.setBalance(request.getBalance());

        Account savedAccount = accountRepository.save(account);
        return modelMapper.map(savedAccount, AccountDto.class);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));

        return modelMapper.map(account, AccountDto.class);
    }

    @Override
    public Page<AccountDto> getAllAccounts(Pageable pageable) {
        Page<Account> accountPage = accountRepository.findAll(pageable);
        return accountPage.map(account -> modelMapper.map(account, AccountDto.class));
    }

    @Override
    public AccountDto deposit(Long id, Double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));

        account.setBalance(account.getBalance() + amount);
        Account updatedAccount = accountRepository.save(account);

        return modelMapper.map(updatedAccount, AccountDto.class);
    }

    @Override
    public AccountDto withdraw(Long id, WithdrawalRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));

        double withdrawalAmount = request.getAmount();
        double totalDeduction = withdrawalAmount + WITHDRAWAL_CHARGE;

        // Check if balance is sufficient for withdrawal + charges
        if (account.getBalance() < totalDeduction) {
            throw new InsufficientBalanceException(
                    String.format("Insufficient balance. Required amount: %.2f (Withdrawal: %.2f + Charges: %.2f), Available balance: %.2f",
                            totalDeduction, withdrawalAmount, WITHDRAWAL_CHARGE, account.getBalance())
            );
        }

        // Perform withdrawal
        double newBalance = account.getBalance() - totalDeduction;
        account.setBalance(newBalance);

        Account updatedAccount = accountRepository.save(account);
        return modelMapper.map(updatedAccount, AccountDto.class);
    }

    @Override
    @Transactional
    public DeleteResponseDTO deleteAccount(Long id) {
        log.info("Starting deletion process for account id: {}", id);
        try {
            log.info("Fetching account with id: {}", id);
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));

            // Check if account has zero balance so as not to delete
            log.info("Found account. Current balance: {}", account.getBalance());
            if (account.getBalance() != 0) {
                log.warn("Cannot delete account {} due to non-zero balance: {}", id, account.getBalance());
                throw new IllegalStateException(
                        String.format("Cannot delete account with non-zero balance: %.2f", account.getBalance())
                );
            }

            log.info("Proceeding with deletion of account id: {}", id);
            accountRepository.deleteById(id);
            log.info("Account deleted from repository");

            log.info("Resetting sequence");
            sequenceManager.resetAccountSequence();
            log.info("Sequence reset completed");

            return DeleteResponseDTO.builder()
                    .message(String.format("Account with id: %d deleted successfully", id))
                    .timestamp(LocalDateTime.now())
                    .id(UUID.randomUUID())
                    .build();

        } catch (Exception e) {
            log.error("Error deleting account with id: {}", id, e);
            throw e;
        }
    }


    @Override
    @Transactional
    public void optimizeIds() {
        sequenceManager.optimizeAccountSequence();
    }
}
