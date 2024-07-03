package com.finSync.entity.repository;

import com.finSync.entity.protfolio.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
    Deposit findByAccountNumberAndUserIdAndDeletedFlag(String accountNumber,Long userId,boolean deletedFlag);
    List<Deposit> findByUserIdAndDeletedFlag(Long userId,boolean deletedFlag);
}

