package com.example.finSync.entity.repository;

import com.example.finSync.entity.protfolio.MutualFund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MutualFundRepository extends JpaRepository<MutualFund, Long> {
    MutualFund findByNameAndUserIdAndDeletedFlag(String name,Long userId,boolean deletedFlag);
    List<MutualFund> findByUserIdAndDeletedFlag(Long userId,boolean deletedFlag);
}
