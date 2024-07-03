package com.finSync.entity.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserPortfolioResponse {
    private Double totalSavingsInBank;
    private Double totalSavingsInDeposit;
    private Double totalLoanAmountPending;
    private MutualFund mutualFunds;
    private Stock stocks;
    private Double totalNetWorth;

    public Double getTotalSavingsInBank() {
        return totalSavingsInBank;
    }

    public void setTotalSavingsInBank(Double totalSavingsInBank) {
        this.totalSavingsInBank = totalSavingsInBank;
    }

    public Double getTotalSavingsInDeposit() {
        return totalSavingsInDeposit;
    }

    public void setTotalSavingsInDeposit(Double totalSavingsInDeposit) {
        this.totalSavingsInDeposit = totalSavingsInDeposit;
    }

    public Double getTotalLoanAmountPending() {
        return totalLoanAmountPending;
    }

    public void setTotalLoanAmountPending(Double totalLoanAmountPending) {
        this.totalLoanAmountPending = totalLoanAmountPending;
    }

    public MutualFund getMutualFund() {
        return mutualFunds;
    }

    public void setMutualFund(MutualFund mutualFunds) {
        this.mutualFunds = mutualFunds;
    }

    public Stock getStock() {
        return stocks;
    }

    public void setStock(Stock stocks) {
        this.stocks = stocks;
    }

    public Double getTotalNetWorth() {
        return totalNetWorth;
    }

    public void setTotalNetWorth(Double totalNetWorth) {
        this.totalNetWorth = totalNetWorth;
    }


    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "{}"; // Return empty object or handle exception as needed
        }
    }
}
