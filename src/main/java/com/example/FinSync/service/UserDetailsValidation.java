package com.example.FinSync.service;

import com.example.FinSync.entity.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailsValidation {

    private final Validator validator =  Validation.buildDefaultValidatorFactory().getValidator();

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsValidation.class);
    public UserDetails validateUserDetails(String signupData){
        UserDetails userDetails;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            userDetails = objectMapper.readValue(signupData, UserDetails.class);
        }catch (Exception e){
            logger.error("Encountering an exception during the parsing of Login data, Exception Details {}",e);
            throw new IllegalArgumentException("Verify that the input JSON data does not adhere to the expected JSON format.");
        }
        validateViolations(userDetails);
        return userDetails;
    }

    public void validateViolations(UserDetails userDetails){
        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<UserDetails> violation : violations) {
                logger.error("Details of Signup Validation Failure: "+violation.getPropertyPath() + ": " + violation.getMessage());
                throw new ValidationException(violation.getMessage());
            }

        }

    }
}
