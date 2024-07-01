package com.example.finSync.controller;

import com.example.finSync.entity.User;
import com.example.finSync.entity.UserProtfolio;
import com.example.finSync.entity.protfolio.*;
import com.example.finSync.entity.repository.*;
import com.example.finSync.entity.response.UserPortfolioResponse;
import com.example.finSync.service.UserPortfolioService;
import com.example.finSync.service.UserWealthCalculation;
import com.example.finSync.service.UserValidator;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class UserWealthController {
    @Autowired
    UserValidator userPortfolioValidator;

    @Autowired
    UserWealthCalculation userWealthCalculation;
    @Autowired
    UserPortfolioService userPortfolioService;
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

    @PostMapping("/user-portfolio")
    public ResponseEntity<String> handleSignup(@RequestHeader("api-token") String userToken, @RequestBody String requestBody) {
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        UserProtfolio userPortfolio = userPortfolioValidator.validateUserPortfolioDetails(user,requestBody);
        userPortfolioService.saveUserWealth(user,userPortfolio);
        UserPortfolioResponse userWealthResponse = userWealthCalculation.getUserWealthResponse(user.getUserName());
        return new ResponseEntity<>(userWealthResponse.toString(), HttpStatus.OK);
    }
    @GetMapping("/accounts")
    public List<Account> getListOfAccounts(@RequestHeader("api-token")String userToken){
        String token = userToken.replace("Bearer","");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        return accountRepository.findByUserIdAndDeletedFlag(user.getUserId(), false).stream().
               map(account ->
                       new Account(
                               account.getAccountNumber(),
                               account.getBankName(),
                               account.getBalance()))
               .collect(Collectors.toList());
    }
    @GetMapping("/deposits")
    public List<Deposit> getListOfDeposits(@RequestHeader("api-token")String userToken){
        String token = userToken.replace("Bearer","");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        return depositRepository.findByUserIdAndDeletedFlag(user.getUserId(), false).stream().
                map(deposit ->
                        new Deposit(
                                deposit.getAccountNumber(),
                                deposit.getType(),
                                deposit.getAmount()))
                .collect(Collectors.toList());


    }
    @GetMapping("/loans")
    public List<Loan> getListOfLoans(@RequestHeader("api-token")String userToken){
        String token = userToken.replace("Bearer","");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        return loanRepository.findByUserIdAndDeletedFlag(user.getUserId(), false).stream().
                map(loan ->
                        new Loan(
                                loan.getAccountNumber(),
                                loan.getType(),
                                loan.getOutstandingAmount(),
                                loan.getPrincipleAmount()))
                .collect(Collectors.toList());
    }
    @GetMapping("/mutualfunds")
    public List<MutualFund> getListOfMutualFunds(@RequestHeader("api-token")String userToken){
        String token = userToken.replace("Bearer","");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        return mutualFundRepository.findByUserIdAndDeletedFlag(user.getUserId(), false).stream().
                map(mutualFund ->
                        new MutualFund(
                                mutualFund.getDematAccountNumber(),
                                mutualFund.getName(),
                                mutualFund.getUnits(),
                                mutualFund.getNav()))
                .collect(Collectors.toList());
    }
    @GetMapping("/stocks")
    public List<Stock> getListOfStocks(@RequestHeader("api-token")String userToken){
        String token = userToken.replace("Bearer","");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        return stockRepository.findByUserIdAndDeletedFlag(user.getUserId(), false).stream().
                map(stock ->
                        new Stock(
                                stock.getDematAccountNumber(),
                                stock.getName(),
                                stock.getQuantity(),
                                stock.getPrice()))
                .collect(Collectors.toList());
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /** @noinspection unused*/
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleExceptionDataIntegrity(Exception ex) {
        return new ResponseEntity<>(Objects.requireNonNull(((DataIntegrityViolationException) ex).getRootCause()).getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
    /** @noinspection unused, unused */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
