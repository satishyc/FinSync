package com.example.finSync.controller;

import com.example.finSync.entity.User;
import com.example.finSync.entity.protfolio.*;
import com.example.finSync.entity.repository.*;
import com.example.finSync.service.UserValidator;
import com.example.finSync.service.UserWealthModifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserWealthModifierController {
    @Autowired
    UserValidator userPortfolioValidator;

    @Autowired
    UserWealthModifier userWealthModifier;


    private static final Logger logger = LoggerFactory.getLogger(UserWealthModifierController.class);


    @PutMapping("/update-account")
    public ResponseEntity<String> updateAccount(@RequestHeader("api-token") String userToken,
                                                 @RequestBody String requestBody){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthModifier.updateAccount(user.getUserId(), requestBody);
        return new ResponseEntity<>("request saved successfully", HttpStatus.OK);
    }
    @PutMapping("/update-deposit")
    public ResponseEntity<String> updateDeposit(@RequestHeader("api-token") String userToken,
                                                 @RequestBody String requestBody){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthModifier.updateDeposit(user.getUserId(), requestBody);
        return new ResponseEntity<>("request saved successfully", HttpStatus.OK);
    }
    @PutMapping("/update-loan")
    public ResponseEntity<String> updateLoan(@RequestHeader("api-token") String userToken,
                                                @RequestBody String requestBody){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthModifier.updateLoan(user.getUserId(), requestBody);
        return new ResponseEntity<>("request saved successfully", HttpStatus.OK);
    }
    @PutMapping("/update-mutualFund")
    public ResponseEntity<String> updateMutualFund(@RequestHeader("api-token") String userToken,
                                             @RequestBody String requestBody){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthModifier.updateMutualFund(user.getUserId(), requestBody);
        return new ResponseEntity<>("request saved successfully", HttpStatus.OK);
    }
    @PutMapping("/update-stocks")
    public ResponseEntity<String> updateStocks(@RequestHeader("api-token") String userToken,
                                                   @RequestBody String requestBody){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthModifier.updateStock(user.getUserId(), requestBody);
        return new ResponseEntity<>("request saved successfully", HttpStatus.OK);
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
