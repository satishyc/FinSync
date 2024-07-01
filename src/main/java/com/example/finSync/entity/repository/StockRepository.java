package com.example.finSync.entity.repository;

import com.example.finSync.entity.protfolio.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findByNameAndUserIdAndDeletedFlag(String name, Long userId,boolean deletedFlag);

    List<Stock> findByUserIdAndDeletedFlag(Long userId,boolean deletedFlag);
}
