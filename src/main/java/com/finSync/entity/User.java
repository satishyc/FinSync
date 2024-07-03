package com.finSync.entity;

import com.finSync.util.PasswordEncryptor;
import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name="user_name",nullable = false)
    private String userName;

    @Column(name="email",nullable = false)
    private String email;


    @Column(name="password",nullable = false)
    @Convert(converter = PasswordEncryptor.class)
    private String password;
    public User() {}
    public User( String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public Long getUserId() {
        return id;
    }

    public void setUserId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password){this.password=password;}

    public String getPassword() {
        return password;
    }


}
