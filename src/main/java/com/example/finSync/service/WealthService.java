package com.example.finSync.service;

import com.example.finSync.entity.mongoWealth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WealthService {
    @Autowired
    BankDetailsRepository bankDetailsRepository;
    @Autowired
    MutualFundPriceRepository mutualFundPriceRepository;
    @Autowired
    StockPriceRepository stockPriceRepository;
    @Cacheable("bankDetails")
    public List<BankDetails> getAllBankDetails() {
        return bankDetailsRepository.findAll();
    }

    @Cacheable("mutualFund")
    public Map<String,MutualFundPrice> getAllMutualFundPrices() {
        Map<String,MutualFundPrice> mfPriceMap = new HashMap<>();
        List<MutualFundPrice> mutualFundPrices = mutualFundPriceRepository.findAll();
        for(MutualFundPrice mfPrice : mutualFundPrices){
            mfPriceMap.put(mfPrice.getName(),mfPrice);
        }


        return mfPriceMap;
    }
    @Cacheable("stock")
    public Map<String,StockPrice> getAllStockPrices() {
        Map<String,StockPrice> stockPriceMap = new HashMap<>();
        List<StockPrice> stockPrices = stockPriceRepository.findAll();
        for(StockPrice stockPrice : stockPrices){
            stockPriceMap.put(stockPrice.getName(),stockPrice);
        }

        return stockPriceMap;
    }


}
