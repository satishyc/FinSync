package com.finSync.service;

import com.finSync.entity.authentication.SignIn;
import com.finSync.entity.authentication.Signup;
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
public class AuthenticationValidator {

    private final Validator validator =  Validation.buildDefaultValidatorFactory().getValidator();

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationValidator.class);
    public Signup validateSignupDetails(Signup signup){
        validateViolations(signup);
        if(!signup.getPassword().equals(signup.getConfirmPassword())){
            throw new ValidationException("Password and ConfirmPassword is not matching, please verify it");
        }
        return signup;
    }

    private void validateViolations(Signup signup){
        Set<ConstraintViolation<Signup>> violations = validator.validate(signup);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Signup> violation : violations) {
                logger.error("Details of Signup Validation Failure: "+violation.getPropertyPath() + ": " + violation.getMessage());
                throw new ValidationException(violation.getMessage());
            }

        }

    }
}
