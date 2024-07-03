package com.finSync.entity.repository;

import com.finSync.entity.protfolio.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Loan findByAccountNumberAndUserIdAndDeletedFlag(String accountNumber,Long userId,boolean deletedFlag);
    List<Loan> findByUserIdAndDeletedFlag(Long userId,boolean deletedFlag);
}
