package com.finSync.entity.authentication;

import com.finSync.util.PasswordEncryptor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Convert;
@Schema(description = "SignIn model", requiredProperties = {"userName","password"})
public class SignIn {
    @Schema(description = "Username of the new user", example = "vivekkb")
    private String userName;
    @Convert(converter = PasswordEncryptor.class)
    @Schema(description = "Password for the new user", example = "12345678")
    private String password;

    public SignIn(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public SignIn() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
