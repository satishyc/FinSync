package com.finSync.entity.mongoWealth;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BankDetailsRepository extends MongoRepository<BankDetails,String> {
    /** @noinspection NullableProblems*/
    List<BankDetails>  findAll();
}
