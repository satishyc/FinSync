package com.example.finSync.entity.protfolio;

import jakarta.validation.constraints.*;

public class Deposit {
    @NotEmpty(message = "accountNumber cannot be blank")
    @Size(min=5,max=20)
    private String accountNumber;
    @Pattern(regexp = "FD|RD|Savings", message = "Deposit type must be FD, RD, or Savings")
    private String type;
    @NotNull
    @Digits(integer = 12, fraction = 2, message = "amount must be zero or positive")
    private Double amount;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
