package com.example.finSync.service;

import com.example.finSync.entity.User;
import com.example.finSync.entity.UserPortfolio;
import com.example.finSync.entity.UserRepository;
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
public class UserPortfolioValidator {
    private final Validator validator =  Validation.buildDefaultValidatorFactory().getValidator();

    private static final Logger logger = LoggerFactory.getLogger(UserPortfolioValidator.class);
    @Autowired
    JwtService jwtService;

    @Autowired
    private UserRepository userRepository;
    public User validatedTokenAndGetUserDetails(String token){
        User user;
        try{
            String userName = jwtService.extractUsername(token);
            user = userRepository.findByUserName(userName);
            if(!jwtService.isTokenValid(token,user)){
                throw new ValidationException("Invalid token details please verify or generate new token by sign in again");
            }
        } catch (Exception e){
            logger.error("Invalid token Exception " +e);
            throw new ValidationException("Invalid token details please verify or generate new token by sign in again");
        }
        return user;

    }
    public UserPortfolio validateUserPortfolioDetails(String userPortfolioData){
        UserPortfolio userPortfolio;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            userPortfolio = objectMapper.readValue(userPortfolioData, UserPortfolio.class);
        }catch (Exception e){
            logger.error("Encountering an exception during the parsing of Signup data, Exception Details : "+e);
            throw new IllegalArgumentException("Verify that the input JSON data does not adhere to the expected JSON format.");
        }
        validateViolations(userPortfolio);

        return userPortfolio;
    }
    private void validateViolations(UserPortfolio userPortfolio){
        Set<ConstraintViolation<UserPortfolio>> violations = validator.validate(userPortfolio);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<UserPortfolio> violation : violations) {
                logger.error("Details of user-portfolio Validation Failure: "+violation.getPropertyPath() + ": " + violation.getMessage());
                throw new ValidationException(violation.getPropertyPath() + ": " + violation.getMessage());
            }

        }

    }

}
