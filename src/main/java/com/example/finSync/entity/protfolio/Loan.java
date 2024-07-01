package com.example.finSync.entity.protfolio;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="loan")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loan_id")
    private Long id;
    @NotEmpty(message = "accountNumber cannot be blank")
    @Size(min=5,max=20)
    @Column(name="account_number")
    private String accountNumber;
    @Pattern(regexp = "personal|home|car|education|agriculture", message = "Loan type must be personal, home, car, education, or agriculture")
    private String type;
    @NotNull(message = "oustandingAmount cannot be balnk")
    @Digits(integer = 12, fraction = 2, message = "outstandingAmount must be zero or positive")
    @Column(name="outstanding_amount")
    private BigDecimal outstandingAmount;
    @NotNull(message ="principleAmount cannot be blank")
    @Digits(integer = 12, fraction = 2, message = "principleAmount must be zero or positive")
    @Column(name="principle_amount")
    private BigDecimal principleAmount;

    @Column(name="user_id")
    private Long userId;

    @Column(name = "create_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_flag")
    @JsonIgnore
    private boolean deletedFlag;

    public Loan(String accountNumber, String type, BigDecimal outstandingAmount, BigDecimal principleAmount) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.outstandingAmount = outstandingAmount;
        this.principleAmount = principleAmount;
    }

    public Loan() {
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(boolean deletedFlag) {
        this.deletedFlag = deletedFlag;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public void setType(String type) {
        this.type = type;
    }


    public BigDecimal getPrincipleAmount() {
        return principleAmount;
    }

    public void setPrincipleAmount(BigDecimal principleAmount) {
        this.principleAmount = principleAmount;
    }
}
