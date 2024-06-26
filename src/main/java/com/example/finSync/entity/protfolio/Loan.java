package com.example.finSync.entity.protfolio;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Loan {
    @NotEmpty(message = "accountNumber cannot be blank")
    @Size(min=5,max=20)
    private String accountNumber;
    @Pattern(regexp = "personal|home|car|education|agriculture", message = "Loan type must be personal, home, car, education, or agriculture")
    private String type;
    @Min(value = 0, message = "outstandingAmount must be zero or positive")
    private Integer outstandingAmount;
    @Min(value = 0, message = "principleAmount must be zero or positive")
    private Integer principleAmount;

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

    public Integer getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(Integer outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public Integer getPrincipleAmount() {
        return principleAmount;
    }

    public void setPrincipleAmount(Integer principleAmount) {
        this.principleAmount = principleAmount;
    }
}
