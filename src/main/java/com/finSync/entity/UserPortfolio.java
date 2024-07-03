package com.finSync.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finSync.entity.protfolio.*;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

public class UserPortfolio {
    @Valid
    private List<Account> accounts = new ArrayList<>();

    @Valid
    private List<Deposit> deposits = new ArrayList<>();
    @Valid
    private List<Loan> loans = new ArrayList<>();
    @Valid
    private List<MutualFund> mutualFunds = new ArrayList<>();

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public List<MutualFund> getMutualFunds() {
        return mutualFunds;
    }

    public void setMutualFunds(List<MutualFund> mutualFunds) {
        this.mutualFunds = mutualFunds;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    private List<Stock> stocks = new ArrayList<>();


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
