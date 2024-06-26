package com.example.finSync.entity.protfolio;

import jakarta.validation.constraints.*;

public class Stock {
    @NotEmpty
    @Size(min=5,max=20)
    private String dematAccountNumber;
    @NotEmpty
    @Size(min=3,max=200,message = "Invalid size for name")
    private String name;
    @NotNull
    @NotNull
    @Min(value=1,message="Minimum size for quantity should be greater than or equal to 1")
    @Max(value=100000,message = "Maximum size for quantity should be less than or equal to 12")
    private int quantity;
    @NotNull
    @Digits(integer = 6, fraction = 2, message = "price must have up to 4 integer digits and 2 fractional digits")
    private Double price;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
