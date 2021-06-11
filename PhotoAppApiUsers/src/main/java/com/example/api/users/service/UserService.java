package com.example.api.users.service;

import com.example.api.users.model.dto.in.UserLoginDtoIn;
import com.example.api.users.model.dto.out.UserDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto model);

    UserDto login(UserLoginDtoIn userLoginDtoIn);
    UserDto loadUserDetailsByEmail(String username);
}
