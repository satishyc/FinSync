package com.example.finSync.service;

import com.example.finSync.entity.User;
import com.example.finSync.entity.protfolio.Account;
import com.example.finSync.entity.protfolio.Deposit;
import com.example.finSync.entity.protfolio.Loan;
import com.example.finSync.entity.repository.AccountRepository;
import com.example.finSync.entity.repository.DepositRepository;
import com.example.finSync.entity.repository.LoanRepository;
import com.example.finSync.entity.repository.MutualFundRepository;
import com.example.finSync.entity.repository.StockRepository;
import com.example.finSync.entity.repository.UserRepository;
import com.example.finSync.entity.response.MutualFund;
import com.example.finSync.entity.response.Stock;
import com.example.finSync.entity.response.UserPortfolioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserWealthCalculation {
    @Autowired
    UserRepository repository;
    @Autowired
    WealthService wealthService;
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

    public UserPortfolioResponse getUserWealthResponse(String userName){
        UserPortfolioResponse response = new UserPortfolioResponse();
        User user = repository.findByUserName(userName);
        Long userId = user.getUserId();
        response.setTotalSavingsInBank(getTotalSavingsBalance(accountRepository.findByUserIdAndDeletedFlag(userId,false)));
        response.setTotalSavingsInDeposit(getTotalDepositsBalance(depositRepository.findByUserIdAndDeletedFlag(userId,false)));
        response.setTotalLoanAmountPending(getTotalLoanDept(loanRepository.findByUserIdAndDeletedFlag(userId,false)));
        response.setMutualFund(getMutualFundDetails(mutualFundRepository.findByUserIdAndDeletedFlag(userId,false)));
        response.setStock(getStockDetails(stockRepository.findByUserIdAndDeletedFlag(userId,false)));
        response.setTotalNetWorth(getTotalNetWorth(response));
        return response;
    }
    private Double getTotalSavingsBalance(List<Account> accounts){
        BigDecimal totalSavings = BigDecimal.valueOf(0.0);
        for(Account account : accounts){
            totalSavings= totalSavings.add(account.getBalance());
        }
        return totalSavings.doubleValue();

    }
    private Double getTotalDepositsBalance(List<Deposit> deposits){
        BigDecimal totalDeposits = BigDecimal.valueOf(0.0);
        for(Deposit deposit : deposits){
            totalDeposits=totalDeposits.add(deposit.getAmount());
        }
        return totalDeposits.doubleValue();
    }
    private Double getTotalLoanDept(List<Loan> loans){
        BigDecimal totalLoanDept = BigDecimal.valueOf(0.0);
        for(Loan loan :  loans){
            totalLoanDept=totalLoanDept.add(loan.getOutstandingAmount());
        }
        return totalLoanDept.doubleValue();
    }
    private MutualFund getMutualFundDetails(List<com.example.finSync.entity.protfolio.MutualFund> mutualFunds){

        double totalInvested = 0.0;
        double currentValue = 0.0;
        for(com.example.finSync.entity.protfolio.MutualFund mf : mutualFunds){

           if(wealthService.getAllMutualFundPrices().get(mf.getName()) !=null) {
               totalInvested+=(mf.getUnits()*mf.getNav().doubleValue());
               currentValue+=(mf.getUnits()*wealthService.getAllMutualFundPrices().get(mf.getName()).getNav());
           }
        }
        return new MutualFund(totalInvested,currentValue,currentValue-totalInvested);
    }
    private Stock getStockDetails(List<com.example.finSync.entity.protfolio.Stock> stocks){
        double totalInvested = 0.0;
        double currentValue = 0.0;
        for(com.example.finSync.entity.protfolio.Stock stock : stocks){
            if(wealthService.getAllStockPrices().get(stock.getName())!=null){
                totalInvested+=(stock.getPrice().doubleValue()*stock.getQuantity());
                currentValue+=(wealthService.getAllStockPrices().get(stock.getName()).getPrice()*stock.getQuantity());
            }
        }
        return new Stock(totalInvested,currentValue,currentValue-totalInvested);
    }
    private Double getTotalNetWorth(UserPortfolioResponse response){
        return (response.getTotalSavingsInBank()+response.getTotalSavingsInDeposit()+response.getStock().getGain()
                +response.getMutualFund().getGain()- response.getTotalLoanAmountPending());
    }

}
