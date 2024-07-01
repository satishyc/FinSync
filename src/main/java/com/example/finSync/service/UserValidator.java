package com.example.finSync.service;

import com.example.finSync.entity.User;
import com.example.finSync.entity.UserProtfolio;
import com.example.finSync.entity.protfolio.Account;
import com.example.finSync.entity.protfolio.Deposit;
import com.example.finSync.entity.protfolio.Loan;
import com.example.finSync.entity.protfolio.MutualFund;
import com.example.finSync.entity.protfolio.Stock;
import com.example.finSync.entity.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public UserProtfolio validateUserPortfolioDetails(User user,String userData){
        UserProtfolio userProtfolio;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            userProtfolio = objectMapper.readValue(userData, UserProtfolio.class);
        }catch (Exception e){
            logger.error("Encountering an exception during the parsing of Signup data, Exception Details : "+e);
            throw new IllegalArgumentException("Verify that the input JSON data does not adhere to the expected JSON format.");
        }
        validateViolations(userProtfolio);
        validateBankMfAndStockNames(userProtfolio);
        mapUserIdsToAccounts(user.getUserId(), userProtfolio);
        return userProtfolio;
    }

    private void validateViolations(UserProtfolio user){
        Set<ConstraintViolation<UserProtfolio>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<UserProtfolio> violation : violations) {
                logger.error("Details of user-portfolio Validation Failure: "+violation.getPropertyPath() + ": " + violation.getMessage());
                throw new ValidationException(violation.getPropertyPath() + ": " + violation.getMessage());
            }

        }

    }
    private void validateBankMfAndStockNames(UserProtfolio user){

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
            if(!wealthService.getAllStockPrices().keySet().contains(stocks.get(i).getName())){
                throw new ValidationException("Stock["+i+"].name is invalid "+stocks.get(i).getName());
            }
        }
        List<MutualFund> mutualFunds = user.getMutualFunds();
        for(int i=0;i< mutualFunds.size();i++){
            if(!wealthService.getAllMutualFundPrices().keySet().contains(mutualFunds.get(i).getName())){
                throw new ValidationException("MutualFund["+i+"].name is invalid "+mutualFunds.get(i).getName());
            }
        }
    }
    private void mapUserIdsToAccounts(Long userId,UserProtfolio userPortfolio){
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
