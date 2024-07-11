package com.finSync.controller;

import com.finSync.entity.User;
import com.finSync.entity.protfolio.Account;
import com.finSync.entity.protfolio.Deposit;
import com.finSync.entity.protfolio.Loan;
import com.finSync.entity.protfolio.MutualFund;
import com.finSync.entity.protfolio.Stock;
import com.finSync.service.UserValidator;
import com.finSync.service.UserWealthDelete;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserWealthDeleteController {
    @Autowired
    UserValidator userPortfolioValidator;

    @Autowired
    UserWealthDelete userWealthDelete;


    @DeleteMapping("/delete-account")
    @Operation(summary = "Remove account data from records", description = "it will delete Account record, " +
            "if it exists else it will through exception")
    @ApiResponse(responseCode = "200", description = "Account Records Removed Successfully")
    @ApiResponse(responseCode = "400", description = "Given Record Not Found")
    @ApiResponse(responseCode = "500",description = "Internal Sever Error")
    public ResponseEntity<String> deleteAccount(@RequestHeader("api-token") String userToken,
                                                @RequestParam String accountNumber){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthDelete.deleteAccount(user.getUserId(), accountNumber.replace("\"",""));
        return new ResponseEntity<>("given record successfully removed", HttpStatus.OK);
    }
    @DeleteMapping("/delete-deposit")
    @Operation(summary = "Remove deposit data from records", description = "it will delete deposit record, " +
            "if it exists else it will through exception")
    @ApiResponse(responseCode = "200", description = "Deposit Records Removed Successfully")
    @ApiResponse(responseCode = "400", description = "Given Record Not Found")
    @ApiResponse(responseCode = "500",description = "Internal Sever Error")
    public ResponseEntity<String> deleteDeposit(@RequestHeader("api-token") String userToken,
                                                @RequestParam String accountNumber){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthDelete.deleteDeposit(user.getUserId(), accountNumber.replace("\"",""));
        return new ResponseEntity<>("given record successfully removed", HttpStatus.OK);
    }
    @DeleteMapping("/delete-loan")
    @Operation(summary = "Remove loan data from records", description = "it will delete loan record, " +
            "if it exists else it will through exception")
    @ApiResponse(responseCode = "200", description = "Loan Records Removed Successfully")
    @ApiResponse(responseCode = "400", description = "Given Record Not Found")
    @ApiResponse(responseCode = "500",description = "Internal Sever Error")
    public ResponseEntity<String> deleteLoan(@RequestHeader("api-token") String userToken,
                                             @RequestParam String accountNumber){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthDelete.deleteLoan(user.getUserId(), accountNumber.replace("\"",""));
        return new ResponseEntity<>("given record successfully removed", HttpStatus.OK);
    }
    @DeleteMapping("/delete-mutualFund")
    @Operation(summary = "Remove mutualFund data from records", description = "it will delete mutualFund record, " +
            "if it exists else it will through exception")
    @ApiResponse(responseCode = "200", description = "MutualFund Records Removed Successfully")
    @ApiResponse(responseCode = "400", description = "Given Record Not Found")
    @ApiResponse(responseCode = "500",description = "Internal Sever Error")
    public ResponseEntity<String> deleteMutualFund(@RequestHeader("api-token") String userToken,
                                                   @RequestParam String mutualFundName){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthDelete.deleteMutualFund(user.getUserId(), mutualFundName.replace("\"",""));
        return new ResponseEntity<>("given record successfully removed", HttpStatus.OK);
    }
    @DeleteMapping("/delete-stocks")
    @Operation(summary = "Remove stocks data from records", description = "it will delete stocks record, " +
            "if it exists else it will through exception")
    @ApiResponse(responseCode = "200", description = "Stocks Records Removed Successfully")
    @ApiResponse(responseCode = "400", description = "Given Record Not Found")
    @ApiResponse(responseCode = "500",description = "Internal Sever Error")
    public ResponseEntity<String> deleteStocks(@RequestHeader("api-token") String userToken,
                                               @RequestParam String stockName){
        String token = userToken.replace("Bearer ", "");
        User user = userPortfolioValidator.validatedTokenAndGetUserDetails(token);
        userWealthDelete.deleteStock(user.getUserId(), stockName.replace("\"",""));
        return new ResponseEntity<>("given record successfully removed", HttpStatus.OK);
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
