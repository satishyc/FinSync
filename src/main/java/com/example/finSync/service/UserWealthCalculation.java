package com.example.finSync.service;

import com.example.finSync.entity.mongoUserProtfolio.UserWealthDetails;
import com.example.finSync.entity.mongoUserProtfolio.UserWealthDetailsRepository;
import com.example.finSync.entity.protfolio.Account;
import com.example.finSync.entity.protfolio.Deposit;
import com.example.finSync.entity.protfolio.Loan;
import com.example.finSync.entity.response.MutualFund;
import com.example.finSync.entity.response.Stock;
import com.example.finSync.entity.response.UserPortfolioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserWealthCalculation {
    @Autowired
    UserWealthDetailsRepository userWealthDetailsRepository;
    @Autowired
    WealthService wealthService;

    public UserPortfolioResponse getUserWealthResponse(String userName){
        UserPortfolioResponse response = new UserPortfolioResponse();
        UserWealthDetails details = userWealthDetailsRepository.findByUserName(userName);
        response.setTotalSavingsInBank(getTotalSavingsBalance(details.getAccounts()));
        response.setTotalSavingsInDeposit(getTotalDepositsBalance(details.getDeposits()));
        response.setTotalLoanAmountPending(getTotalLoanDept(details.getLoans()));
        response.setMutualFund(getMutualFundDetails(details.getMutualFunds()));
        response.setStock(getStockDetails(details.getStocks()));
        response.setTotalNetWorth(getTotalNetworth(response));
        return response;
    }
    private Double getTotalSavingsBalance(List<Account> accounts){
        Double totalSavings = 0.0;
        for(Account account : accounts){
            totalSavings+= account.getBalance();
        }
        return totalSavings;

    }
    private Double getTotalDepositsBalance(List<Deposit> deposits){
        Double totalDeposits = 0.0;
        for(Deposit deposit : deposits){
            totalDeposits+=deposit.getAmount();
        }
        return totalDeposits;
    }
    private Double getTotalLoanDept(List<Loan> loans){
        Double totalLoanDept = 0.0;
        for(Loan loan :  loans){
            totalLoanDept+=loan.getOutstandingAmount();
        }
        return totalLoanDept;
    }
    private MutualFund getMutualFundDetails(List<com.example.finSync.entity.protfolio.MutualFund> mutualFunds){

        Double totalInvested = 0.0;
        Double currentValue = 0.0;
        for(com.example.finSync.entity.protfolio.MutualFund mf : mutualFunds){

           if(wealthService.getAllMutualFundPrices().get(mf.getName()) !=null) {
               totalInvested+=(mf.getUnits()*mf.getNav());
               currentValue+=(mf.getUnits()*wealthService.getAllMutualFundPrices().get(mf.getName()).getNav());
           }
        }
        return new MutualFund(totalInvested,currentValue,currentValue-totalInvested);
    }
    private Stock getStockDetails(List<com.example.finSync.entity.protfolio.Stock> stocks){
        Double totalInvested = 0.0;
        Double currentValue = 0.0;
        for(com.example.finSync.entity.protfolio.Stock stock : stocks){
            if(wealthService.getAllStockPrices().get(stock.getName())!=null){
                totalInvested+=(stock.getPrice()*stock.getQuantity());
                currentValue+=(wealthService.getAllStockPrices().get(stock.getName()).getPrice()*stock.getQuantity());
            }
        }
        return new Stock(totalInvested,currentValue,currentValue-totalInvested);
    }
    private Double getTotalNetworth(UserPortfolioResponse response){
        return (response.getTotalSavingsInBank()+response.getTotalSavingsInDeposit()+response.getStock().getGain()
                +response.getMutualFund().getGain()- response.getTotalLoanAmountPending());
    }

}
