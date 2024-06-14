package com.example.FinSync.controller;

import com.example.FinSync.entity.User;
import com.example.FinSync.entity.UserDetails;
import com.example.FinSync.service.UserDetailsValidation;
import com.example.FinSync.service.UserService;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {

    @Autowired
    UserDetailsValidation userDetailsValidation;

    @Autowired
    UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);

    @PostMapping("/signup")
    public ResponseEntity<String> handlePostRequest(@RequestBody String requestBody) {
        logger.debug("Received POST Request for signup {}"+requestBody);
        UserDetails userDetails = userDetailsValidation.validateUserDetails(requestBody);
        userService.registerUser(new User(userDetails.getUserName(), userDetails.getEmailId(), userDetails.getPassword()));


        return new ResponseEntity<>("", HttpStatus.OK);
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
