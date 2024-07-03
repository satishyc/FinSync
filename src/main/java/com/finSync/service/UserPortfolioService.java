package com.finSync.service;

import com.finSync.entity.User;
import com.finSync.entity.UserProtfolio;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public void saveUserWealth(User user, UserProtfolio userProtfolio){
        saveAccounts(user,userProtfolio.getAccounts());
        saveDeposits(user,userProtfolio.getDeposits());
        saveLoans(user,userProtfolio.getLoans());
        saveMutualFunds(user,userProtfolio.getMutualFunds());
        saveStocks(user,userProtfolio.getStocks());
    }
    private void saveAccounts(User user, List<Account> accountList){
        List<Account> records = accountRepository.findByUserIdAndDeletedFlag(user.getUserId(),false);
        if(records==null || records.size()==0){
            accountRepository.saveAll(accountList);
        }else{
            for(Account account : accountList){
                Account persistedAccount = accountRepository.findByAccountNumberAndUserIdAndDeletedFlag(account.getAccountNumber(),
                        user.getUserId(),false);
                if(persistedAccount==null){
                    accountRepository.save(account);
                }else{
                    persistedAccount.setBalance(account.getBalance());
                    accountRepository.save(persistedAccount);
                }
            }
        }

    }
    private void saveDeposits(User user, List<Deposit> depositList){
        List<Deposit> records = depositRepository.findByUserIdAndDeletedFlag(user.getUserId(),false);
        if(records==null || records.size()==0){
            depositRepository.saveAll(depositList);
        }else{
            for(Deposit deposit : depositList){
                Deposit persistedDeposit = depositRepository.findByAccountNumberAndUserIdAndDeletedFlag(deposit.getAccountNumber(),
                        user.getUserId(), false);
                if(persistedDeposit==null){
                    depositRepository.save(deposit);
                }else{
                    persistedDeposit.setAmount(deposit.getAmount());
                    depositRepository.save(persistedDeposit);
                }
            }
        }
    }
    private void saveLoans(User user, List<Loan> loanList){
        List<Loan> records = loanRepository.findByUserIdAndDeletedFlag(user.getUserId(),false);
        if(records==null || records.size()==0){
            loanRepository.saveAll(loanList);
        }else{
            for(Loan loan : loanList){
                Loan persisteLoan = loanRepository.findByAccountNumberAndUserIdAndDeletedFlag(loan.getAccountNumber(),
                        user.getUserId(), false);
                if(persisteLoan==null){
                    loanRepository.save(loan);
                }else{
                    persisteLoan.setOutstandingAmount(loan.getOutstandingAmount());
                    loanRepository.save(persisteLoan);
                }

            }
        }
    }
    private void saveMutualFunds(User user, List<MutualFund> mutualFundList){
        List<MutualFund> records = mutualFundRepository.findByUserIdAndDeletedFlag(user.getUserId(),false);
        if(records==null || records.size()==0){
            mutualFundRepository.saveAll(mutualFundList);
        }else{
            for(MutualFund mutualFund : mutualFundList){
                MutualFund persistedMf = mutualFundRepository.findByNameAndUserIdAndDeletedFlag(mutualFund.getName(),
                        user.getUserId(),false );
                if(persistedMf==null){
                    mutualFundRepository.save(mutualFund);
                }else{
                    persistedMf.setNav(mutualFund.getNav());
                    persistedMf.setUnits(mutualFund.getUnits());
                    mutualFundRepository.save(persistedMf);
                }
            }
        }
    }
    private void saveStocks(User user, List<Stock> stockList){
        List<Stock> records = stockRepository.findByUserIdAndDeletedFlag(user.getUserId(),false);
        if(records == null || records.size()==0){
            stockRepository.saveAll(stockList);
        }else{
            for(Stock stock : stockList){
                Stock persistedStock = stockRepository.findByNameAndUserIdAndDeletedFlag(stock.getName(), user.getUserId(),
                        false);
                if(persistedStock==null){
                    stockRepository.save(stock);
                }else{
                    persistedStock.setPrice(stock.getPrice());
                    persistedStock.setQuantity(stock.getQuantity());
                    stockRepository.save(persistedStock);
                }
            }
        }
    }

}

