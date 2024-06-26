package com.example.finSync.entity.protfolio;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Deposit {
    @NotEmpty(message = "accountNumber cannot be blank")
    @Size(min=5,max=20)
    private String accountNumber;
    @Pattern(regexp = "FD|RD|Savings", message = "Deposit type must be FD, RD, or Savings")
    private String type;
    @Min(value = 0, message = "balance must be zero or positive")
    private Integer amount;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
