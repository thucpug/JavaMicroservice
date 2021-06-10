package com.example.api.users.service;

import com.example.api.users.model.UserModelRequest;
import com.example.api.users.model.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto model);
}
