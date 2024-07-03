package com.finSync.entity.mongoWealth;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MutualFundPriceRepository extends MongoRepository<MutualFundPrice,String> {
    /** @noinspection NullableProblems*/
    @Override
    List<MutualFundPrice> findAll();


}
