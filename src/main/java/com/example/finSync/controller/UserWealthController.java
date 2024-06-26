package com.example.finSync.controller;

import com.example.finSync.entity.User;
import com.example.finSync.entity.UserPortfolio;
import com.example.finSync.entity.response.UserPortfolioResponse;
import com.example.finSync.service.UserWealthCalculation;
import com.example.finSync.service.UserWealthDetailsService;
import com.example.finSync.service.UserPortfolioValidator;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class UserWealthController {
    @Autowired
    UserPortfolioValidator userPortfolioValidator;
    @Autowired
    UserWealthDetailsService userWealthDetailsService;
    @Autowired
    UserWealthCalculation userWealthCalculation;

    @PostMapping("/user-portfolio")
    public ResponseEntity<String> handleSignup(@RequestHeader("api-token") String userToken, @RequestBody String requestBody) {
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        UserPortfolio userPortfolio = userPortfolioValidator.validateUserPortfolioDetails(requestBody);
        userWealthDetailsService.saveUserWealth(user,userPortfolio);
        UserPortfolioResponse userWealthResponse = userWealthCalculation.getUserWealthResponse(user.getUserName());
        return new ResponseEntity<>(userWealthResponse.toString(), HttpStatus.OK);
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
