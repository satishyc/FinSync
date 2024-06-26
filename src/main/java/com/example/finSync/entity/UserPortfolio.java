package com.example.finSync.entity;

import com.example.finSync.entity.protfolio.*;
import jakarta.validation.Valid;

import java.util.List;

public class UserPortfolio {
    @Valid
    private List<Account> accounts;
    @Valid
    private List<Deposit> deposits;
    @Valid
    private List<Loan> loans;
    @Valid
    private List<MutualFund> mutualFunds;
    @Valid
    private List<Stock> stocks;

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
}
