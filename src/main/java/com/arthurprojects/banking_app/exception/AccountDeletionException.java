package com.arthurprojects.banking_app.exception;

public class AccountDeletionException extends RuntimeException{
    public AccountDeletionException(String message) {
        super(message);
    }
}
