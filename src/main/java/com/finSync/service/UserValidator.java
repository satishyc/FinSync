package com.finSync.service;

import com.finSync.entity.User;
import com.finSync.entity.UserPortfolio;
import com.finSync.entity.protfolio.*;
import com.finSync.entity.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserValidator {
    private final Validator validator =  Validation.buildDefaultValidatorFactory().getValidator();

    private static final Logger logger = LoggerFactory.getLogger(UserValidator.class);
    @Autowired
    JwtService jwtService;

    @Autowired
    WealthService wealthService;

    @Autowired
    private UserRepository userRepository;
    public User validatedTokenAndGetUserDetails(String token){
        try {
            String userName = jwtService.extractUsername(token);
            User user = userRepository.findByUserName(userName);

            if (user == null || !jwtService.isTokenValid(token, user)) {
                throw new ValidationException("Invalid token details. Please verify or generate a new token by signing in again.");
            }

            return user;
        } catch (Exception e) {
            logger.error("Invalid token exception: ", e);
            throw new ValidationException("Invalid token details. Please verify or generate a new token by signing in again.");
        }

    }
    public void validateUserPortfolioDetails(User user, UserPortfolio userPortfolio){
        validateViolations(userPortfolio);
        validateBankMfAndStockNames(userPortfolio);
        mapUserIdsToAccounts(user.getUserId(), userPortfolio);

    }

    private void validateViolations(UserPortfolio user) {
        Set<ConstraintViolation<UserPortfolio>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<UserPortfolio> violation : violations) {
                String message = violation.getPropertyPath() + ": " + violation.getMessage();
                logger.error("Details of user-portfolio validation failure: " + message);
                throw new ValidationException(message);
            }
        }
    }
    private void validateBankMfAndStockNames(UserPortfolio user){

        List<Account> accounts = user.getAccounts();
        for(int i=0;i< accounts.size();i++){
            if(!wealthService.getBankShortNames().contains(accounts.get(i).getBankName())){
                throw new ValidationException("Account["+i+"].bankName is invalid "+accounts.get(i).getBankName());
            }
        }
        Set<String> accNos = new HashSet<>();
        for(int i=0;i<accounts.size();i++){
            if(accNos.contains(accounts.get(i).getAccountNumber())){
                throw new ValidationException("Account["+i+"].accountNumber is already in use please change or verify " +
                        "the account number");
            }else{
                accNos.add(accounts.get(i).getAccountNumber());
            }
        }
        accNos.clear();
        List<Deposit> depositAccounts = user.getDeposits();
        for(int i=0;i<depositAccounts.size();i++){
            if(accNos.contains(depositAccounts.get(i).getAccountNumber())){
                throw new ValidationException("Deposit["+i+"].accountNumber is already in use please change or verify " +
                        "the account number");
            }else{
                accNos.add(depositAccounts.get(i).getAccountNumber());
            }
        }
        accNos.clear();
        List<Loan> loanAccounts = user.getLoans();
        for(int i=0;i<loanAccounts.size();i++){
            if(accNos.contains(loanAccounts.get(i).getAccountNumber())){
                throw new ValidationException("Loan["+i+"].accountNumber is already in use please change or verify " +
                        "the account number");
            }else{
                accNos.add(loanAccounts.get(i).getAccountNumber());
            }
        }

        List<Stock> stocks = user.getStocks();
        for(int i=0;i<stocks.size();i++){
            if(!wealthService.getAllStockPrices().containsKey(stocks.get(i).getName())){
                throw new ValidationException("Stock["+i+"].name is invalid "+stocks.get(i).getName());
            }
        }
        List<MutualFund> mutualFunds = user.getMutualFunds();
        for(int i=0;i< mutualFunds.size();i++){
            if(!wealthService.getAllMutualFundPrices().containsKey(mutualFunds.get(i).getName())){
                throw new ValidationException("MutualFund["+i+"].name is invalid "+mutualFunds.get(i).getName());
            }
        }
    }
    private void mapUserIdsToAccounts(Long userId, UserPortfolio userPortfolio){
        if (userPortfolio.getAccounts() != null) {
            userPortfolio.getAccounts().forEach(account -> account.setUserId(userId));
        }
        if (userPortfolio.getDeposits() != null) {
            userPortfolio.getDeposits().forEach(deposit -> deposit.setUserId(userId));
        }
        if (userPortfolio.getLoans() != null) {
            userPortfolio.getLoans().forEach(loan -> loan.setUserId(userId));
        }
        if (userPortfolio.getMutualFunds() != null) {
            userPortfolio.getMutualFunds().forEach(mutualFund -> mutualFund.setUserId(userId));
        }
        if (userPortfolio.getStocks() != null) {
            userPortfolio.getStocks().forEach(stock -> stock.setUserId(userId));
        }
    }

}
