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

    public void deleteAccount(Long userId,String accountNumber){
        Account record = accountRepository.findByAccountNumberAndUserIdAndDeletedFlag(accountNumber,
                userId, false);
        if(record==null){
            throw new ValidationException("Account details not found in records");
        }else{
            record.setDeletedFlag(true);
            accountRepository.save(record);
        }
    }
    public void deleteDeposit(Long userId,String accountNumber){
        Deposit record = depositRepository.findByAccountNumberAndUserIdAndDeletedFlag(accountNumber,
                userId, false);
        if(record==null){
            throw new ValidationException("Deposit details not found in records");
        }else{
            record.setDeletedFlag(true);
           depositRepository.save(record);
        }
    }
    public void deleteLoan(Long userId,String accountNumber){

        Loan record = loanRepository.findByAccountNumberAndUserIdAndDeletedFlag(accountNumber,
                userId, false);
        if(record==null){
            throw new ValidationException("Loan details not found in records");
        }else{
            record.setDeletedFlag(true);
            loanRepository.save(record);
        }
    }
    public void deleteMutualFund(Long userId,String name){
        MutualFund record = mutualFundRepository.findByNameAndUserIdAndDeletedFlag(name,
                userId, false);
        if(record==null){
            throw new ValidationException("MutualFund details not found in records");
        }else{
            record.setDeletedFlag(true);
            mutualFundRepository.save(record);
        }
    }
    public void deleteStock(Long userId,String stockName){
        Stock record = stockRepository.findByNameAndUserIdAndDeletedFlag(stockName,
                userId, false);
        if(record==null){
            throw new ValidationException("Stock details not found in records");
        }else{
            record.setDeletedFlag(true);
            stockRepository.save(record);
        }
    }
    private void validateAccountViolations(Account account) {
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Account> violation : violations) {
                String errorMessage = String.format("%s: %s", violation.getPropertyPath(), violation.getMessage());
                logger.error("Details of account validation failure: " + errorMessage);
                throw new ValidationException(errorMessage);
            }
        }
    }
    private void validateDepositViolations(Deposit deposit){
        Set<ConstraintViolation<Deposit>> violations = validator.validate(deposit);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Deposit> violation : violations) {
                String errorMessage = String.format("%s: %s", violation.getPropertyPath(), violation.getMessage());
                logger.error("Details of deposit validation failure: " + errorMessage);
                throw new ValidationException(errorMessage);
            }
            }

    }


    private void validateLoanViolations(Loan loan){
        Set<ConstraintViolation<Loan>> violations = validator.validate(loan);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Loan> violation : violations) {
                String errorMessage = String.format("%s: %s", violation.getPropertyPath(), violation.getMessage());
                logger.error("Details of loan validation failure: " + errorMessage);
                throw new ValidationException(errorMessage);
            }

        }

    }
    private void validateMutualFundViolations(MutualFund mutualFund){
        Set<ConstraintViolation<MutualFund>> violations = validator.validate(mutualFund);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<MutualFund> violation : violations) {
                String errorMessage = String.format("%s: %s", violation.getPropertyPath(), violation.getMessage());
                logger.error("Details of mutualFund validation failure: " + errorMessage);
                throw new ValidationException(errorMessage);
            }

        }

    }
    private void validateStockViolations(Stock stock){
        Set<ConstraintViolation<Stock>> violations = validator.validate(stock);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Stock> violation : violations) {
                String errorMessage = String.format("%s: %s", violation.getPropertyPath(), violation.getMessage());
                logger.error("Details of stock validation failure: " + errorMessage);
                throw new ValidationException(errorMessage);
            }

        }

    }
}
