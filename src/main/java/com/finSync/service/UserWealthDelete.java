package com.finSync.service;

import com.finSync.entity.protfolio.*;
import com.finSync.entity.repository.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserWealthDelete {
    private static final Logger logger = LoggerFactory.getLogger(UserWealthDelete.class);

    private final Validator validator =  Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    DepositRepository depositRepository;
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    MutualFundRepository mutualFundRepository;

    @Autowired
    StockRepository stockRepository;

    public void deleteAccount(Long userId,Account account){
        validateAccountViolations(account);
        Account record = accountRepository.findByAccountNumberAndUserIdAndDeletedFlag(account.getAccountNumber(),
                userId, false);
        if(record==null){
            throw new ValidationException("given details not found in records");
        }else{
            record.setDeletedFlag(true);
            accountRepository.save(record);
        }
    }
    public void deleteDeposit(Long userId,Deposit deposit){
        validateDepositViolations(deposit);
        Deposit record = depositRepository.findByAccountNumberAndUserIdAndDeletedFlag(deposit.getAccountNumber(),
                userId, false);
        if(record==null){
            throw new ValidationException("given details not found in records");
        }else{
            record.setDeletedFlag(true);
           depositRepository.save(record);
        }
    }
    public void deleteLoan(Long userId,Loan loan){

        validateLoanViolations(loan);
        Loan record = loanRepository.findByAccountNumberAndUserIdAndDeletedFlag(loan.getAccountNumber(),
                userId, false);
        if(record==null){
            throw new ValidationException("given details not found in records");
        }else{
            record.setDeletedFlag(true);
            loanRepository.save(record);
        }
    }
    public void deleteMutualFund(Long userId,MutualFund mutualFund){
        validateMutualFundViolations(mutualFund);
        MutualFund record = mutualFundRepository.findByNameAndUserIdAndDeletedFlag(mutualFund.getName(),
                userId, false);
        if(record==null){
            throw new ValidationException("given details not found in records");
        }else{
            record.setDeletedFlag(true);
            mutualFundRepository.save(record);
        }
    }
    public void deleteStock(Long userId,Stock stock){
        validateStockViolations(stock);
        Stock record = stockRepository.findByNameAndUserIdAndDeletedFlag(stock.getName(),
                userId, false);
        if(record==null){
            throw new ValidationException("given details not found in records");
        }else{
            record.setDeletedFlag(true);
            stockRepository.save(record);
        }
    }
    private void validateAccountViolations(Account account){
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Account> violation : violations) {
                logger.error("Details of user-portfolio Validation Failure: "+violation.getPropertyPath() + ": " + violation.getMessage());
                throw new ValidationException(violation.getPropertyPath() + ": " + violation.getMessage());
            }

        }

    }
    private void validateDepositViolations(Deposit deposit){
        Set<ConstraintViolation<Deposit>> violations = validator.validate(deposit);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Deposit> violation : violations) {
                logger.error("Details of user-portfolio Validation Failure: "+violation.getPropertyPath() + ": " + violation.getMessage());
                throw new ValidationException(violation.getPropertyPath() + ": " + violation.getMessage());
            }

        }

    }
    private void validateLoanViolations(Loan loan){
        Set<ConstraintViolation<Loan>> violations = validator.validate(loan);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Loan> violation : violations) {
                logger.error("Details of user-portfolio Validation Failure: "+violation.getPropertyPath() + ": " + violation.getMessage());
                throw new ValidationException(violation.getPropertyPath() + ": " + violation.getMessage());
            }

        }

    }
    private void validateMutualFundViolations(MutualFund mutualFund){
        Set<ConstraintViolation<MutualFund>> violations = validator.validate(mutualFund);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<MutualFund> violation : violations) {
                logger.error("Details of user-portfolio Validation Failure: "+violation.getPropertyPath() + ": " + violation.getMessage());
                throw new ValidationException(violation.getPropertyPath() + ": " + violation.getMessage());
            }

        }

    }
    private void validateStockViolations(Stock stock){
        Set<ConstraintViolation<Stock>> violations = validator.validate(stock);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Stock> violation : violations) {
                logger.error("Details of user-portfolio Validation Failure: "+violation.getPropertyPath() + ": " + violation.getMessage());
                throw new ValidationException(violation.getPropertyPath() + ": " + violation.getMessage());
            }

        }

    }
}
