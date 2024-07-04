package com.finSync.controller;

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
import com.finSync.entity.response.UserPortfolioResponse;
import com.finSync.service.UserPortfolioService;
import com.finSync.service.UserValidator;
import com.finSync.service.UserWealthCalculation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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
    @Operation(summary = "Add user portfolio", description = "Add the user portfolio data")
    @ApiResponse(responseCode = "200", description = "User Records Added Successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input",content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "String")})
    @ApiResponse(responseCode = "500",description = "Internal Sever Error",content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "String")})
    public UserPortfolioResponse handleUserPortfolio(@RequestHeader("api-token") String userToken, @RequestBody UserPortfolio userPortfolio) {
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userPortfolioValidator.validateUserPortfolioDetails(user,userPortfolio);
        userPortfolioService.saveUserWealth(user,userPortfolio);
        return userWealthCalculation.getUserWealthResponse(user.getUserName());

    }
    @GetMapping("/get-user-wealth")
    @Operation(summary = "Get user Wealth", description = "Returns Summary of User Wealth")
    @ApiResponse(responseCode = "200", description = "Returns User Wealth")
    @ApiResponse(responseCode = "400", description = "Invalid input",content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "String")})
    @ApiResponse(responseCode = "500",description = "Internal Sever Error",content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "String")})
    public UserPortfolioResponse getCurrentWealth(@RequestHeader("api-token")String userToken){
        String token = userToken.replace("Bearer","");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        return  userWealthCalculation.getUserWealthResponse(user.getUserName());

    }

    @GetMapping("/user-data")
    @Operation(summary = "Get user Data", description = "Returns all user data which is available in system")
    @ApiResponse(responseCode = "200", description = "Returns all user data")
    @ApiResponse(responseCode = "400", description = "Invalid input",content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "String")})
    @ApiResponse(responseCode = "500",description = "Internal Sever Error",content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "String")})
    public UserPortfolio getUserData(@RequestHeader("api-token")String userToken){
        String token = userToken.replace("Bearer","");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        UserPortfolio portfolio = new UserPortfolio();
        portfolio.setAccounts(accountRepository.findByUserIdAndDeletedFlag(user.getUserId(), false).stream().
                map(account ->
                        new Account(
                                account.getAccountNumber(),
                                account.getBankName(),
                                account.getBalance()))
                .collect(Collectors.toList()));
        portfolio.setDeposits(depositRepository.findByUserIdAndDeletedFlag(user.getUserId(), false).stream().
                map(deposit ->
                        new Deposit(
                                deposit.getAccountNumber(),
                                deposit.getType(),
                                deposit.getAmount()))
                .collect(Collectors.toList()));
        portfolio.setLoans(loanRepository.findByUserIdAndDeletedFlag(user.getUserId(), false).stream().
                map(loan ->
                        new Loan(
                                loan.getAccountNumber(),
                                loan.getType(),
                                loan.getOutstandingAmount(),
                                loan.getPrincipleAmount()))
                .collect(Collectors.toList()));
        portfolio.setMutualFunds(mutualFundRepository.findByUserIdAndDeletedFlag(user.getUserId(), false).stream().
                map(mutualFund ->
                        new MutualFund(
                                mutualFund.getDematAccountNumber(),
                                mutualFund.getName(),
                                mutualFund.getUnits(),
                                mutualFund.getNav()))
                .collect(Collectors.toList()));
        portfolio.setStocks(stockRepository.findByUserIdAndDeletedFlag(user.getUserId(), false).stream().
                map(stock ->
                        new Stock(
                                stock.getDematAccountNumber(),
                                stock.getName(),
                                stock.getQuantity(),
                                stock.getPrice()))
                .collect(Collectors.toList()));
        return portfolio;
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
