package com.finSync.service;

import com.finSync.entity.User;
import com.finSync.entity.protfolio.Account;
import com.finSync.entity.protfolio.Deposit;
import com.finSync.entity.protfolio.Loan;
import com.finSync.entity.repository.AccountRepository;
import com.finSync.entity.repository.DepositRepository;
import com.finSync.entity.repository.LoanRepository;
import com.finSync.entity.repository.MutualFundRepository;
import com.finSync.entity.repository.StockRepository;
import com.finSync.entity.repository.UserRepository;
import com.finSync.entity.response.MutualFundResponse;
import com.finSync.entity.response.StockResponse;
import com.finSync.entity.response.UserPortfolioResponse;
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
    private MutualFundResponse getMutualFundDetails(List<com.finSync.entity.protfolio.MutualFund> mutualFunds){

        double totalInvested = 0.0;
        double currentValue = 0.0;
        for(com.finSync.entity.protfolio.MutualFund mf : mutualFunds){

           if(wealthService.getAllMutualFundPrices().get(mf.getName()) !=null) {
               totalInvested+=(mf.getUnits()*mf.getNav().doubleValue());
               currentValue+=(mf.getUnits()*wealthService.getAllMutualFundPrices().get(mf.getName()).getNav());
           }
        }
        return new MutualFundResponse(totalInvested,currentValue,currentValue-totalInvested);
    }
    private StockResponse getStockDetails(List<com.finSync.entity.protfolio.Stock> stocks){
        double totalInvested = 0.0;
        double currentValue = 0.0;
        for(com.finSync.entity.protfolio.Stock stock : stocks){
            if(wealthService.getAllStockPrices().get(stock.getName())!=null){
                totalInvested+=(stock.getPrice().doubleValue()*stock.getQuantity());
                currentValue+=(wealthService.getAllStockPrices().get(stock.getName()).getPrice()*stock.getQuantity());
            }
        }
        return new StockResponse(totalInvested,currentValue,currentValue-totalInvested);
    }
    private Double getTotalNetWorth(UserPortfolioResponse response){
        return (response.getTotalSavingsInBank()+response.getTotalSavingsInDeposit()+response.getStock().getGain()
                +response.getMutualFund().getGain()- response.getTotalLoanAmountPending());
    }

}
