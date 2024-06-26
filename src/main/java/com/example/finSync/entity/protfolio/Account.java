package com.example.finSync.entity.protfolio;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Account {
    @NotEmpty(message = "accountNumber cannot be blank")
    @Size(min=5,max=20)
    private String accountNumber;
    private String bankName;
    @Min(value = 0, message = "balance must be zero or positive")
    private Integer balance;

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

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
