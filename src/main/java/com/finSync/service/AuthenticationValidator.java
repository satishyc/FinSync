package com.finSync.service;

import com.finSync.entity.authentication.Signup;
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
    public void validateSignupDetails(Signup signup){
        validateViolations(signup);
        if(!signup.getPassword().equals(signup.getConfirmPassword())){
            throw new ValidationException("Password and ConfirmPassword is not matching, please verify it");
        }
    }

    private void validateViolations(Signup signup) {
        Set<ConstraintViolation<Signup>> violations = validator.validate(signup);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Signup> violation : violations) {
                String message = violation.getPropertyPath() + ": " + violation.getMessage();
                logger.error("Details of Signup validation failure: " + message);
                throw new ValidationException(message);
            }
        }
    }

}
