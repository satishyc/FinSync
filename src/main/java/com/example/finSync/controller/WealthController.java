package com.example.finSync.controller;

import com.example.finSync.entity.mongoWealth.BankDetails;
import com.example.finSync.entity.mongoWealth.MutualFundPrice;
import com.example.finSync.entity.mongoWealth.StockPrice;
import com.example.finSync.service.WealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController

public class WealthController {

    @Autowired
    WealthService wealthService;

    @GetMapping("/bankNames")
    public List<BankDetails> getBankDetails() {
        return wealthService.getAllBankDetails();
    }
    @GetMapping("/mutualFundNames")
    public List<String> getMutualFundDetails() {
        return wealthService.getAllMutualFundPrices().keySet().stream().map(Object::toString)
                .collect(Collectors.toList());

    }
    @GetMapping("/stockNames")
    public List<String> getStockPrices() {
        return wealthService.getAllStockPrices().keySet().stream().map(Object::toString).collect(Collectors.toList());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
