package com.example.finSync.entity.mongoUserProtfolio;

import com.example.finSync.entity.protfolio.*;
import jakarta.validation.Valid;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "userWealth")

public class UserWealthDetails {
    @Id
    private String id;
    private String userName;
    private List<Account> accounts;
    private List<Deposit> deposits;
    private List<Loan> loans;
    private List<MutualFund> mutualFunds;
    private List<Stock> stocks;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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
