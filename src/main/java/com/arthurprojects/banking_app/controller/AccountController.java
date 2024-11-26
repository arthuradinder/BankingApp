package com.arthurprojects.banking_app.controller;

import com.arthurprojects.banking_app.dto.AccountDto;
import com.arthurprojects.banking_app.dto.CreateAccountRequest;
import com.arthurprojects.banking_app.dto.WithdrawalRequest;
import com.arthurprojects.banking_app.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor

public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@Valid @RequestBody CreateAccountRequest request) {
        return new ResponseEntity<>(accountService.createAccount(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping
    public ResponseEntity<Page<AccountDto>> getAllAccounts(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(accountService.getAllAccounts(pageable));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        if (amount == null) {
            throw new IllegalArgumentException("Amount is required");
        }
        return ResponseEntity.ok(accountService.deposit(id, amount));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(
            @PathVariable Long id,
            @Valid @RequestBody WithdrawalRequest request) {
        return ResponseEntity.ok(accountService.withdraw(id, request));
    }
}
