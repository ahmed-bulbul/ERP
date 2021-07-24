package com.bulbul.erp.entity;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class JwtRequest {

    @NotBlank(message = "username must not be blank")
    String username;

    @Size(message = "password size must be 5 to 14", min = 4,max = 14)
    @NotBlank
    String password;

    public JwtRequest() {
    }

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
