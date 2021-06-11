package com.example.api.users.controller;

import com.example.api.users.common.mapper.UserMapperRequest;
import com.example.api.users.model.dto.in.UserLoginDtoIn;
import com.example.api.users.model.dto.in.UserSignInDtoIn;
import com.example.api.users.model.dto.out.UserDto;
import com.example.api.users.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    private final Environment environment;
    private final UserService userService;
    private final UserMapperRequest userMapper;

    public UserController(Environment environment, @Lazy UserService userService, UserMapperRequest userMapper) {
        this.environment = environment;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(path = "/user")
    public String get() {
        return "user  Thuc is working D!" + environment.getProperty("local.server.port");
    }

    @PostMapping(path = "/create")
    public UserDto create(@Valid @RequestBody UserSignInDtoIn userSignInDtoIn) {
        UserDto userDto = userMapper.toEntity(userSignInDtoIn);
        return userService.createUser(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> post(@Valid @RequestBody UserLoginDtoIn userLoginDtoIn) {
        UserDto userDto = userService.login(userLoginDtoIn);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }
}
