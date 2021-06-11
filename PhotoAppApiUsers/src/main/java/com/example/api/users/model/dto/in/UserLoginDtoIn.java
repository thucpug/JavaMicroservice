package com.example.api.users.model.dto.in;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserLoginDtoIn implements Serializable {
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 8)
    private  String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
