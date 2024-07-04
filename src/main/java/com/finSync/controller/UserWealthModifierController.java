package com.finSync.controller;

import com.finSync.entity.User;
import com.finSync.entity.protfolio.Account;
import com.finSync.entity.protfolio.Deposit;
import com.finSync.entity.protfolio.Loan;
import com.finSync.entity.protfolio.MutualFund;
import com.finSync.entity.protfolio.Stock;
import com.finSync.service.UserValidator;
import com.finSync.service.UserWealthModifier;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserWealthModifierController {
    @Autowired
    UserValidator userPortfolioValidator;

    @Autowired
    UserWealthModifier userWealthModifier;




    @PutMapping("/update-account")
    @Operation(summary = "update account data from the existing records", description = "it will update Account record, " +
            "if it exists else it will through exception")
    @ApiResponse(responseCode = "200", description = "Account Record Updated Successfully")
    @ApiResponse(responseCode = "400", description = "Given Record Not Found")
    @ApiResponse(responseCode = "500",description = "Internal Sever Error")
    public ResponseEntity<String> updateAccount(@RequestHeader("api-token") String userToken,
                                                 @RequestBody Account account){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthModifier.updateAccount(user.getUserId(), account);
        return new ResponseEntity<>("request saved successfully", HttpStatus.OK);
    }
    @PutMapping("/update-deposit")
    @Operation(summary = "update deposit data from the existing records", description = "it will update deposit record, " +
            "if it exists else it will through exception")
    @ApiResponse(responseCode = "200", description = "deposit Record Updated Successfully")
    @ApiResponse(responseCode = "400", description = "Given Record Not Found")
    @ApiResponse(responseCode = "500",description = "Internal Sever Error")
    public ResponseEntity<String> updateDeposit(@RequestHeader("api-token") String userToken,
                                                 @RequestBody Deposit deposit){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthModifier.updateDeposit(user.getUserId(), deposit);
        return new ResponseEntity<>("request saved successfully", HttpStatus.OK);
    }
    @PutMapping("/update-loan")
    @Operation(summary = "update loan data from the existing records", description = "it will update loan record, " +
            "if it exists else it will through exception")
    @ApiResponse(responseCode = "200", description = "loan Record Updated Successfully")
    @ApiResponse(responseCode = "400", description = "Given Record Not Found")
    @ApiResponse(responseCode = "500",description = "Internal Sever Error")
    public ResponseEntity<String> updateLoan(@RequestHeader("api-token") String userToken,
                                                @RequestBody Loan loan){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthModifier.updateLoan(user.getUserId(), loan);
        return new ResponseEntity<>("request saved successfully", HttpStatus.OK);
    }
    @PutMapping("/update-mutualFund")
    @Operation(summary = "update mutualFund data from the existing records", description = "it will update mutualFund record, " +
            "if it exists else it will through exception")
    @ApiResponse(responseCode = "200", description = "mutualFund Record Updated Successfully")
    @ApiResponse(responseCode = "400", description = "Given Record Not Found")
    @ApiResponse(responseCode = "500",description = "Internal Sever Error")
    public ResponseEntity<String> updateMutualFund(@RequestHeader("api-token") String userToken,
                                             @RequestBody MutualFund mutualFund){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthModifier.updateMutualFund(user.getUserId(), mutualFund);
        return new ResponseEntity<>("request saved successfully", HttpStatus.OK);
    }
    @PutMapping("/update-stocks")
    @Operation(summary = "update stocks data from the existing records", description = "it will update stocks record, " +
            "if it exists else it will through exception")
    @ApiResponse(responseCode = "200", description = "stocks Record Updated Successfully")
    @ApiResponse(responseCode = "400", description = "Given Record Not Found")
    @ApiResponse(responseCode = "500",description = "Internal Sever Error")
    public ResponseEntity<String> updateStocks(@RequestHeader("api-token") String userToken,
                                                   @RequestBody Stock stock){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthModifier.updateStock(user.getUserId(), stock);
        return new ResponseEntity<>("request saved successfully", HttpStatus.OK);
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
