package com.example.FinSync.controller;

import com.example.FinSync.entity.SignIn;
import com.example.FinSync.entity.User;
import com.example.FinSync.entity.Signup;
import com.example.FinSync.service.AuthenticationValidator;
import com.example.FinSync.service.JwtService;
import com.example.FinSync.service.UserService;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthenticationValidator authenticationValidator;

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signup")
    public ResponseEntity<String> handleSignup(@RequestBody String requestBody) {
        Signup signup = authenticationValidator.validateSignupDetails(requestBody);
        userService.registerUser(new User(signup.getUserName(), signup.getEmailId(), signup.getPassword()));


        return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<String> handleLogin(@RequestBody String requestBody){
        SignIn signIn = authenticationValidator.validateSignInDetails(requestBody);
        if(userService.isItValidUser(signIn)){
            String jwtToken = jwtService.generateToken(userService.getUserDetails(signIn));
            return new ResponseEntity<>("Token = "+jwtToken, HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Login Details", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleExceptionDataIntegrity(Exception ex) {
        return new ResponseEntity<>(((DataIntegrityViolationException) ex).getRootCause().getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
