package com.example.api.users.controller;

import com.example.api.users.common.mapper.UserMapper;
import com.example.api.users.common.mapper.UserMapperRequest;
import com.example.api.users.model.UserModelRequest;
import com.example.api.users.model.dto.UserDto;
import com.example.api.users.service.UserService;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    private final Environment environment;
    private final UserService userService;
    private final UserMapperRequest userMapper;

    public UserController(Environment environment, UserService userService, UserMapperRequest userMapper) {
        this.environment = environment;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(path = "/user")
    public String get() {
        return "user  Thuc is working!" + environment.getProperty("local.server.port");
    }

    @PostMapping(path = "create")
    public UserDto create(@Valid @RequestBody UserModelRequest userModelRequest) {
        UserDto userDto = userMapper.toEntity(userModelRequest);
        return userService.createUser(userDto);

    }
}
