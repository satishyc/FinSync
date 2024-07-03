package com.finSync.entity.mongoWealth;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StockPriceRepository extends MongoRepository<StockPrice,String> {
    /** @noinspection NullableProblems*/
    @Override
    List<StockPrice> findAll();

}
