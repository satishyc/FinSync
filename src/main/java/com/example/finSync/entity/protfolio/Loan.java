package com.example.finSync.entity.protfolio;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.*;

public class Loan {
    @NotEmpty(message = "accountNumber cannot be blank")
    @Size(min=5,max=20)
    private String accountNumber;
    @Pattern(regexp = "personal|home|car|education|agriculture", message = "Loan type must be personal, home, car, education, or agriculture")
    private String type;
    @NotNull
    @Digits(integer = 12, fraction = 2, message = "outstandingAmount must be zero or positive")
    private Double outstandingAmount;
    @NotNull
    @Digits(integer = 12, fraction = 2, message = "principleAmount must be zero or positive")
    private Double principleAmount;

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

    public Double getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(Double outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public Double getPrincipleAmount() {
        return principleAmount;
    }

    public void setPrincipleAmount(Double principleAmount) {
        this.principleAmount = principleAmount;
    }
}
