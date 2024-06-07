package com.example.FinSync.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {

    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);

    @PostMapping("/signup")
    public ResponseEntity<String> handlePostRequest(@RequestBody String requestBody) {

        logger.debug("Received POST Request for signup {}"+requestBody);


        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

}
