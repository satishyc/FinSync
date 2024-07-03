package com.finSync.entity.authentication;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;


public class Signup {

    @NotEmpty(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(description = "Username of the new user", example = "vivekkb", required = true)
    private String userName;

    @NotNull
    @Email(message = "Please provide valid email ID")
    @Schema(description = "Email ID of the new user", example = "vivekkb@gmail.com", required = true)
    private String emailId;
    @NotEmpty(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Schema(description = "Password for the new user", example = "12345678", required = true)
    private String password;

    @NotEmpty(message = "confirmPassword cannot be blank")
    @Size(min = 8, message = "confirmPassword must be at least 8 characters")
    @Schema(description = "Confirmation of the password", example = "12345678", required = true)
    private String confirmPassword;

    public Signup(String userName, String emailId, String password, String confirmPassword) {
        this.userName = userName;
        this.emailId = emailId;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public Signup() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
