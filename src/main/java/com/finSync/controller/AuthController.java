package com.finSync.controller;

import com.finSync.entity.User;
import com.finSync.entity.authentication.SignIn;
import com.finSync.entity.authentication.SignInResponse;
import com.finSync.entity.authentication.Signup;
import com.finSync.service.AuthenticationValidator;
import com.finSync.service.JwtService;
import com.finSync.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
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

    /** @noinspection unused*/
    @PostMapping("/signup")
    @Operation(summary = "Register a new user", description = "Registers a new user with the provided details")
    @ApiResponse(responseCode = "200", description = "User Registered Successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "500",description = "Internal Sever Error")
    public ResponseEntity<String> handleSignup(@RequestBody Signup signup) {
        authenticationValidator.validateSignupDetails(signup);
        userService.registerUser(new User(signup.getUserName(), signup.getEmailId(), signup.getPassword()));
        return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);
    }
    @PostMapping("/login")
    @Operation(summary = "Login with user", description = "Login with already registered details")
    @ApiResponse(responseCode = "200", description = "Login Successful with api-token response", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
            schema = @Schema(implementation = SignInResponse.class))})
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "500",description = "Internal Sever Error")
    public ResponseEntity<String> handleLogin(@RequestBody SignIn signIn){
        SignInResponse response = new SignInResponse();
        if(userService.isItValidUser(signIn)){
            String jwtToken = jwtService.generateToken(userService.getUserDetails(signIn));
            response.setToken(jwtToken);
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Login Details", HttpStatus.BAD_REQUEST);
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
