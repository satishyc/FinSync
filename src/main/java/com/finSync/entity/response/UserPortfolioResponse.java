package com.finSync.entity.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class UserPortfolioResponse {
    private Double totalSavingsInBank;
    private Double totalSavingsInDeposit;
    private Double totalLoanAmountPending;
    private MutualFundResponse mutualFundsResponse;
    private StockResponse stocks;
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

    public MutualFundResponse getMutualFund() {
        return mutualFundsResponse;
    }

    public void setMutualFund(MutualFundResponse mutualFundsResponse) {
        this.mutualFundsResponse = mutualFundsResponse;
    }

    public StockResponse getStock() {
        return stocks;
    }

    public void setStock(StockResponse stocks) {
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
