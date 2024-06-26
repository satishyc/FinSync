package com.example.finSync.entity.protfolio;

import jakarta.validation.constraints.*;

public class Account {
    @NotEmpty(message = "accountNumber cannot be blank")
    @Size(min=5,max=20)
    private String accountNumber;
    private String bankName;
    @NotNull
    @Digits(integer = 12, fraction = 2,  message = "balance must be zero or positive")
    private Double balance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
