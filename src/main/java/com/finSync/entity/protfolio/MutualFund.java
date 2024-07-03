package com.finSync.entity.protfolio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="mutual_funds")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MutualFund {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mutualFunds_id")
    @Schema(hidden = true)
    private Long id;
    @NotEmpty(message = "dematAccountNumber cannot be blank")
    @Size(min=5,max=20)
    @Column(name="demat_account_number")
    private String dematAccountNumber;
    @NotEmpty
    @Size(min=5,max=200 ,message="Invalid size for name")
    private String name;
    @NotNull
    @Min(value=1,message="Minimum size for units should be greater than or equal to 1")
    @Max(value=100000,message = "Maximum size for units should be less than or equal to 12")
    private int units;
    @NotNull
    @Digits(integer = 6, fraction = 2, message = "nav must have up to 4 integer digits and 2 fractional digits")
    private BigDecimal nav;

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

    public MutualFund(String dematAccountNumber, String name, int units, BigDecimal nav) {
        this.dematAccountNumber = dematAccountNumber;
        this.name = name;
        this.units = units;
        this.nav = nav;
    }

    public MutualFund() {
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

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public BigDecimal getNav() {
        return nav;
    }

    public void setNav(BigDecimal nav) {
        this.nav = nav;
    }
}
