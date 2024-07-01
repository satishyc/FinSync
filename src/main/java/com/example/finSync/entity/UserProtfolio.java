package com.example.finSync.entity;

import com.example.finSync.entity.protfolio.Account;
import com.example.finSync.entity.protfolio.Deposit;
import com.example.finSync.entity.protfolio.Loan;
import com.example.finSync.entity.protfolio.MutualFund;
import com.example.finSync.entity.protfolio.Stock;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

public class UserProtfolio {
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


}
