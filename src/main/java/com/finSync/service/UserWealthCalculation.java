package com.finSync.service;

import com.finSync.entity.User;
import com.finSync.entity.protfolio.Account;
import com.finSync.entity.protfolio.Deposit;
import com.finSync.entity.protfolio.Loan;
import com.finSync.entity.protfolio.MutualFund;
import com.finSync.entity.protfolio.Stock;
import com.finSync.entity.repository.*;
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
    private UserRepository userRepository;

    @Autowired
    private WealthService wealthService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private MutualFundRepository mutualFundRepository;

    @Autowired
    private StockRepository stockRepository;

    public UserPortfolioResponse getUserWealthResponse(String userName) {
        User user = userRepository.findByUserName(userName);
        Long userId = user.getUserId();
        List<Account> accounts = accountRepository.findByUserIdAndDeletedFlag(userId, false);
        List<Deposit> deposits = depositRepository.findByUserIdAndDeletedFlag(userId, false);
        List<Loan> loans = loanRepository.findByUserIdAndDeletedFlag(userId, false);
        List<MutualFund> mutualFunds = mutualFundRepository.findByUserIdAndDeletedFlag(userId, false);
        List<Stock> stocks = stockRepository.findByUserIdAndDeletedFlag(userId, false);

        Double totalSavingsInBank = getTotalSavingsBalance(accounts);
        Double totalSavingsInDeposit = getTotalDepositsBalance(deposits);
        Double totalLoanAmountPending = getTotalLoanDept(loans);
        MutualFundResponse mutualFundResponse = getMutualFundDetails(mutualFunds);
        StockResponse stockResponse = getStockDetails(stocks);
        Double totalNetWorth = getTotalNetWorth(totalSavingsInBank, totalSavingsInDeposit, stockResponse.getGain(), mutualFundResponse.getGain(), totalLoanAmountPending);

        return new UserPortfolioResponse(totalSavingsInBank, totalSavingsInDeposit, totalLoanAmountPending, mutualFundResponse, stockResponse, totalNetWorth);
    }

    private Double getTotalSavingsBalance(List<Account> accounts) {
        return accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();
    }

    private Double getTotalDepositsBalance(List<Deposit> deposits) {
        return deposits.stream()
                .map(Deposit::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();
    }

    private Double getTotalLoanDept(List<Loan> loans) {
        return loans.stream()
                .map(Loan::getOutstandingAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();
    }

    private MutualFundResponse getMutualFundDetails(List<MutualFund> mutualFunds) {
        double totalInvested = 0.0;
        double currentValue = 0.0;
        for (MutualFund mf : mutualFunds) {
            if (wealthService.getAllMutualFundPrices().containsKey(mf.getName())) {
                totalInvested += (mf.getUnits() * mf.getNav().doubleValue());
                currentValue += (mf.getUnits() * wealthService.getAllMutualFundPrices().get(mf.getName()).getNav());
            }
        }
        return new MutualFundResponse(totalInvested, currentValue, currentValue - totalInvested);
    }

    private StockResponse getStockDetails(List<Stock> stocks) {
        double totalInvested = 0.0;
        double currentValue = 0.0;
        for (Stock stock : stocks) {
            if (wealthService.getAllStockPrices().containsKey(stock.getName())) {
                totalInvested += (stock.getPrice().doubleValue() * stock.getQuantity());
                currentValue += (wealthService.getAllStockPrices().get(stock.getName()).getPrice() * stock.getQuantity());
            }
        }
        return new StockResponse(totalInvested, currentValue, currentValue - totalInvested);
    }

    private Double getTotalNetWorth(Double totalSavingsInBank, Double totalSavingsInDeposit, Double stockGain, Double mutualFundGain, Double totalLoanAmountPending) {
        return totalSavingsInBank + totalSavingsInDeposit + stockGain + mutualFundGain - totalLoanAmountPending;
    }
}
