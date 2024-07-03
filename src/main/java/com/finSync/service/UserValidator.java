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
        User user;
        try{
            String userName = jwtService.extractUsername(token);
            user = userRepository.findByUserName(userName);
            if(!jwtService.isTokenValid(token,user)){
                throw new ValidationException("Invalid token details please verify or generate new token by sign in again");
            }
        } catch (Exception e){
            logger.error("Invalid token Exception " +e);
            throw new ValidationException("Invalid token details please verify or generate new token by sign in again");
        }
        return user;

    }
    public void validateUserPortfolioDetails(User user, UserPortfolio userPortfolio){
        validateViolations(userPortfolio);
        validateBankMfAndStockNames(userPortfolio);
        mapUserIdsToAccounts(user.getUserId(), userPortfolio);

    }

    private void validateViolations(UserPortfolio user){
        Set<ConstraintViolation<UserPortfolio>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<UserPortfolio> violation : violations) {
                logger.error("Details of user-portfolio Validation Failure: "+violation.getPropertyPath() + ": " + violation.getMessage());
                throw new ValidationException(violation.getPropertyPath() + ": " + violation.getMessage());
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
            for (Account account : userPortfolio.getAccounts()) {
                account.setUserId(userId);
            }
        }
        if (userPortfolio.getDeposits() != null) {
            for (Deposit deposit : userPortfolio.getDeposits()) {
                deposit.setUserId(userId);
            }
        }
        if (userPortfolio.getLoans() != null) {
            for (Loan loan : userPortfolio.getLoans()) {
                loan.setUserId(userId);
            }
        }
        if (userPortfolio.getMutualFunds() != null) {
            for (MutualFund mutualFund : userPortfolio.getMutualFunds()) {
                mutualFund.setUserId(userId);
            }
        }
        if (userPortfolio.getStocks() != null) {
            for (Stock stock : userPortfolio.getStocks()) {
                stock.setUserId(userId);
            }
        }
    }

}
