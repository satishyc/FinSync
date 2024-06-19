package com.example.finSync.entity.mongoWealth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface StockPriceRepository extends MongoRepository<StockPrice,String> {
    @Override
    List<StockPrice> findAll();
    @Override
    void deleteAll();
}
