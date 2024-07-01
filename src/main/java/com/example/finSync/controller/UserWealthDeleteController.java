package com.example.finSync.controller;

import com.example.finSync.entity.User;
import com.example.finSync.service.UserValidator;
import com.example.finSync.service.UserWealthDelete;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class UserWealthDeleteController {
    @Autowired
    UserValidator userPortfolioValidator;

    @Autowired
    UserWealthDelete userWealthDelete;


    private static final Logger logger = LoggerFactory.getLogger(UserWealthDeleteController.class);


    @DeleteMapping("/delete-account")
    public ResponseEntity<String> updateAccount(@RequestHeader("api-token") String userToken,
                                                @RequestBody String requestBody){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthDelete.deleteAccount(user.getUserId(), requestBody);
        return new ResponseEntity<>("request saved successfully", HttpStatus.OK);
    }
    @DeleteMapping("/delete-deposit")
    public ResponseEntity<String> updateDeposit(@RequestHeader("api-token") String userToken,
                                                @RequestBody String requestBody){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthDelete.deleteDeposit(user.getUserId(), requestBody);
        return new ResponseEntity<>("request saved successfully", HttpStatus.OK);
    }
    @DeleteMapping("/delete-loan")
    public ResponseEntity<String> updateLoan(@RequestHeader("api-token") String userToken,
                                             @RequestBody String requestBody){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthDelete.deleteLoan(user.getUserId(), requestBody);
        return new ResponseEntity<>("request saved successfully", HttpStatus.OK);
    }
    @DeleteMapping("/delete-mutualFund")
    public ResponseEntity<String> updateMutualFund(@RequestHeader("api-token") String userToken,
                                                   @RequestBody String requestBody){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthDelete.deleteMutualFund(user.getUserId(), requestBody);
        return new ResponseEntity<>("request saved successfully", HttpStatus.OK);
    }
    @DeleteMapping("/delete-stocks")
    public ResponseEntity<String> updateStocks(@RequestHeader("api-token") String userToken,
                                               @RequestBody String requestBody){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthDelete.deleteStock(user.getUserId(), requestBody);
        return new ResponseEntity<>("request saved successfully", HttpStatus.OK);
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
