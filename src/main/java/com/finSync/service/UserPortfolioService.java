package com.finSync.service;

import com.finSync.entity.User;
import com.finSync.entity.UserPortfolio;
import com.finSync.entity.protfolio.Account;
import com.finSync.entity.protfolio.Deposit;
import com.finSync.entity.protfolio.Loan;
import com.finSync.entity.protfolio.MutualFund;
import com.finSync.entity.protfolio.Stock;
import com.finSync.entity.repository.AccountRepository;
import com.finSync.entity.repository.DepositRepository;
import com.finSync.entity.repository.LoanRepository;
import com.finSync.entity.repository.MutualFundRepository;
import com.finSync.entity.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserPortfolioService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    DepositRepository depositRepository;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    MutualFundRepository mutualFundRepository;
    @Autowired
    StockRepository stockRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserPortfolioService.class);


    public void saveUserWealth(User user, UserPortfolio userPortfolio){
        try {
            saveAccounts(user, userPortfolio.getAccounts());
            saveDeposits(user, userPortfolio.getDeposits());
            saveLoans(user, userPortfolio.getLoans());
            saveMutualFunds(user, userPortfolio.getMutualFunds());
            saveStocks(user, userPortfolio.getStocks());
        } catch (Exception e) {
            logger.error("Failed to save user wealth for user: " + user.getUserName(), e);
            throw new RuntimeException("Failed to save user wealth. Please try again later.");
        }
    }
    private void saveAccounts(User user, List<Account> accountList){
        List<Account> existingAccounts  = accountRepository.findByUserIdAndDeletedFlag(user.getUserId(),false);
        if(existingAccounts ==null || existingAccounts .size()==0){
            accountRepository.saveAll(accountList);
        }else{
            Map<String, Account> accountMap = existingAccounts.stream()
                    .collect(Collectors.toMap(Account::getAccountNumber, account -> account));
            for (Account account : accountList) {
                Account persistedAccount = accountMap.get(account.getAccountNumber());
                if (persistedAccount == null) {
                    accountRepository.save(account);
                } else {
                    persistedAccount.setBalance(account.getBalance());
                    accountRepository.save(persistedAccount);
                }
            }
        }

    }
    private void saveDeposits(User user, List<Deposit> depositList){
        List<Deposit> existingDeposits = depositRepository.findByUserIdAndDeletedFlag(user.getUserId(),false);
        if(existingDeposits==null || existingDeposits.size()==0){
            depositRepository.saveAll(depositList);
        }else{
            Map<String, Deposit> depositMap = existingDeposits.stream()
                    .collect(Collectors.toMap(Deposit::getAccountNumber, deposit -> deposit));

            for (Deposit deposit : depositList) {
                Deposit persistedDeposit = depositMap.get(deposit.getAccountNumber());
                if (persistedDeposit == null) {
                    depositRepository.save(deposit);
                } else {
                    persistedDeposit.setAmount(deposit.getAmount());
                    depositRepository.save(persistedDeposit);
                }
            }
        }
    }
    private void saveLoans(User user, List<Loan> loanList) {
        List<Loan> existingLoans = loanRepository.findByUserIdAndDeletedFlag(user.getUserId(), false);

        if (existingLoans == null || existingLoans.isEmpty()) {
            loanRepository.saveAll(loanList);
        } else {
            Map<String, Loan> loanMap = existingLoans.stream()
                    .collect(Collectors.toMap(Loan::getAccountNumber, loan -> loan));

            for (Loan loan : loanList) {
                Loan persistedLoan = loanMap.get(loan.getAccountNumber());
                if (persistedLoan == null) {
                    loanRepository.save(loan);
                } else {
                    persistedLoan.setOutstandingAmount(loan.getOutstandingAmount());
                    loanRepository.save(persistedLoan);
                }
            }
        }
    }

    private void saveMutualFunds(User user, List<MutualFund> mutualFundList) {
        List<MutualFund> existingMutualFunds = mutualFundRepository.findByUserIdAndDeletedFlag(user.getUserId(), false);

        if (existingMutualFunds == null || existingMutualFunds.isEmpty()) {
            mutualFundRepository.saveAll(mutualFundList);
        } else {
            Map<String, MutualFund> mutualFundMap = existingMutualFunds.stream()
                    .collect(Collectors.toMap(MutualFund::getName, mutualFund -> mutualFund));

            for (MutualFund mutualFund : mutualFundList) {
                MutualFund persistedMutualFund = mutualFundMap.get(mutualFund.getName());
                if (persistedMutualFund == null) {
                    mutualFundRepository.save(mutualFund);
                } else {
                    persistedMutualFund.setNav(mutualFund.getNav());
                    persistedMutualFund.setUnits(mutualFund.getUnits());
                    mutualFundRepository.save(persistedMutualFund);
                }
            }
        }
    }

    private void saveStocks(User user, List<Stock> stockList) {
        List<Stock> existingStocks = stockRepository.findByUserIdAndDeletedFlag(user.getUserId(), false);

        if (existingStocks == null || existingStocks.isEmpty()) {
            stockRepository.saveAll(stockList);
        } else {
            Map<String, Stock> stockMap = existingStocks.stream()
                    .collect(Collectors.toMap(Stock::getName, stock -> stock));

            for (Stock stock : stockList) {
                Stock persistedStock = stockMap.get(stock.getName());
                if (persistedStock == null) {
                    stockRepository.save(stock);
                } else {
                    persistedStock.setPrice(stock.getPrice());
                    persistedStock.setQuantity(stock.getQuantity());
                    stockRepository.save(persistedStock);
                }
            }
        }
    }


}

