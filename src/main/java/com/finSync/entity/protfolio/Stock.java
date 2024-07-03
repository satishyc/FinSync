package com.finSync.entity.protfolio;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="stocks")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stocks_id")
    @Schema(hidden = true)
    private Long id;
    @NotEmpty
    @Size(min=5,max=20)
    @Column(name="demat_account_number")
    private String dematAccountNumber;
    @NotEmpty
    @Size(min=3,max=200,message = "Invalid size for name")
    private String name;

    @NotNull
    @Min(value=1,message="Minimum size for quantity should be greater than or equal to 1")
    @Max(value=100000,message = "Maximum size for quantity should be less than or equal to 12")
    private int quantity;
    @NotNull
    @Digits(integer = 6, fraction = 2, message = "price must have up to 4 integer digits and 2 fractional digits")
    private BigDecimal price;

    @Column(name="user_id")
    @Schema(hidden = true)
    private Long userId;

    @Column(name = "create_at")
    @CreationTimestamp
    @Schema(hidden = true)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @Schema(hidden = true)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_flag")
    @JsonIgnore
    @Schema(hidden = true)
    private boolean deletedFlag;

    public Stock(String dematAccountNumber, String name, int quantity, BigDecimal price) {
        this.dematAccountNumber = dematAccountNumber;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Stock() {
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

    public String getDematAccountNumber() {
        return dematAccountNumber;
    }

    public void setDematAccountNumber(String dematAccountNumber) {
        this.dematAccountNumber = dematAccountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
