package com.finSync.entity.repository;

import com.finSync.entity.protfolio.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumberAndUserIdAndDeletedFlag(String accountNumber,Long UserId,boolean deletedFlag);
    List<Account> findByUserIdAndDeletedFlag(Long userId,boolean deletedFlag);

}
