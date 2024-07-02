package com.example.finSync.service;

import com.example.finSync.entity.protfolio.*;
import com.example.finSync.entity.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class UserWealthModifier {
    private static final Logger logger = LoggerFactory.getLogger(UserWealthModifier.class);

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

    public void updateAccount(Long userId,String requestBody){
        Account account;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            account = objectMapper.readValue(requestBody, Account.class);
        }catch (Exception e){
            logger.error("Encountering an exception during the parsing of Signup data, Exception Details : "+e);
            throw new IllegalArgumentException("Verify that the input JSON data does not adhere to the expected JSON format.");
        }
        validateAccountViolations(account);
        Account record = accountRepository.findByAccountNumberAndUserIdAndDeletedFlag(account.getAccountNumber(),
                userId, false);
        if(record==null){
            throw new ValidationException("given details not found in records");
        }else{
            record.setBalance(account.getBalance());
            record.setBankName(account.getBankName());
            accountRepository.save(record);
        }
    }
    public void updateDeposit(Long userId,String requestBody){
        Deposit deposit;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            deposit = objectMapper.readValue(requestBody, Deposit.class);
        }catch (Exception e){
            logger.error("Encountering an exception during the parsing of Signup data, Exception Details : "+e);
            throw new IllegalArgumentException("Verify that the input JSON data does not adhere to the expected JSON format.");
        }
        validateDepositViolations(deposit);
        Deposit record = depositRepository.findByAccountNumberAndUserIdAndDeletedFlag(deposit.getAccountNumber(),
                userId, false);
        if(record==null){
            throw new ValidationException("given details not found in records");
        }else{
            record.setType(deposit.getType());
            record.setAmount(deposit.getAmount());
            depositRepository.save(record);
        }
    }
    public void updateLoan(Long userId,String requestBody){
        Loan loan;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            loan = objectMapper.readValue(requestBody, Loan.class);
        }catch (Exception e){
            logger.error("Encountering an exception during the parsing of Signup data, Exception Details : "+e);
            throw new IllegalArgumentException("Verify that the input JSON data does not adhere to the expected JSON format.");
        }
        validateLoanViolations(loan);
        Loan record = loanRepository.findByAccountNumberAndUserIdAndDeletedFlag(loan.getAccountNumber(),
                userId, false);
        if(record==null){
            throw new ValidationException("given details not found in records");
        }else{
            record.setType(loan.getType());
            record.setOutstandingAmount(loan.getOutstandingAmount());
            record.setPrincipleAmount(loan.getPrincipleAmount());
            loanRepository.save(record);
        }
    }
    public void updateMutualFund(Long userId,String requestBody){
        MutualFund mutualFund;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            mutualFund = objectMapper.readValue(requestBody, MutualFund.class);
        }catch (Exception e){
            logger.error("Encountering an exception during the parsing of Signup data, Exception Details : "+e);
            throw new IllegalArgumentException("Verify that the input JSON data does not adhere to the expected JSON format.");
        }
        validateMutualFundViolations(mutualFund);
        MutualFund record = mutualFundRepository.findByNameAndUserIdAndDeletedFlag(mutualFund.getName(),
                userId, false);
        if(record==null){
            throw new ValidationException("given details not found in records");
        }else{
            record.setNav(mutualFund.getNav());
            record.setUnits(mutualFund.getUnits());
            record.setName(mutualFund.getName());
            mutualFundRepository.save(record);
        }
    }
    public void updateStock(Long userId,String requestBody){
        Stock stock;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            stock = objectMapper.readValue(requestBody, Stock.class);
        }catch (Exception e){
            logger.error("Encountering an exception during the parsing of Signup data, Exception Details : "+e);
            throw new IllegalArgumentException("Verify that the input JSON data does not adhere to the expected JSON format.");
        }
        validateStockViolations(stock);
        Stock record = stockRepository.findByNameAndUserIdAndDeletedFlag(stock.getName(),
                userId, false);
        if(record==null){
            throw new ValidationException("given details not found in records");
        }else{
            record.setPrice(stock.getPrice());
            record.setQuantity(stock.getQuantity());
            record.setName(stock.getName());
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
