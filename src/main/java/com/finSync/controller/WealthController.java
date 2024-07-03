package com.finSync.controller;

import com.finSync.entity.mongoWealth.BankDetails;

import com.finSync.service.WealthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController

public class WealthController {

    @Autowired
    WealthService wealthService;

    @GetMapping("/bankNames")
    @Operation(summary = "Bank Names", description = "List of Bank Names supported in Account Schema")
    @ApiResponse(responseCode = "200", description = "Returns List of supported Banks")
    @ApiResponse(responseCode = "500",description = "Internal Sever Error",content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "String")})
    public List<BankDetails> getBankDetails() {
        return wealthService.getAllBankDetails();
    }
    @GetMapping("/mutualFundNames")
    @Operation(summary = "MutualFund Names", description = "List of MutualFund Names Names supported in MutualFund Schema")
    @ApiResponse(responseCode = "200", description = "Returns List of supportedMutualFunds")
    @ApiResponse(responseCode = "500",description = "Internal Sever Error",content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "String")})
    public List<String> getMutualFundDetails() {
        return wealthService.getAllMutualFundPrices().keySet().stream().map(Object::toString)
                .collect(Collectors.toList());

    }
    @GetMapping("/stockNames")
    @Operation(summary = "Stock Names", description = "List of Stock Names supported in Stock Schema")
    @ApiResponse(responseCode = "200", description = "Returns List of supported Banks")
    @ApiResponse(responseCode = "500",description = "Internal Sever Error",content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "String")})
    public List<String> getStockPrices() {
        return wealthService.getAllStockPrices().keySet().stream().map(Object::toString).collect(Collectors.toList());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
