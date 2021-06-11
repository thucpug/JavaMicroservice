package com.example.api.users.model.dto.in;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserSignInDtoIn implements Serializable {
    @NotNull(message = "Firstname must not be null")
    @Size(min = 2,message = "FirstName must not be less than two characters")
    private String firstName;
    private  String lastName;
    @NotNull(message = "Password not be null")
    @Size(min = 8, message = "Password must not be less 8 characters")
    private String password;
    @NotNull
    @Email(message = "Email must be a valid email")
    private  String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

