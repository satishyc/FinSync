package com.example.finSync.entity.protfolio;

import jakarta.validation.constraints.*;

public class MutualFund {
    @NotEmpty(message = "dematAccountNumber cannot be blank")
    @Size(min=5,max=20)
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
    private Double nav;

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

    public Double getNav() {
        return nav;
    }

    public void setNav(Double nav) {
        this.nav = nav;
    }
}
