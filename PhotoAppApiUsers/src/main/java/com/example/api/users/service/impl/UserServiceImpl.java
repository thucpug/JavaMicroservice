package com.example.api.users.service.impl;

import com.example.api.users.common.mapper.UserMapper;
import com.example.api.users.model.dto.UserDto;
import com.example.api.users.model.entity.UserEntity;
import com.example.api.users.repository.UserRepository;
import com.example.api.users.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(UserDto model) {
        model.setUserId(UUID.randomUUID().toString());
        UserEntity entity = userMapper.toEntity(model);
        entity.setEncryptedPassword(passwordEncoder.encode(model.getPassword()));
        UserEntity userEntity = userRepository.save(entity);
        return userMapper.toDto(userEntity);
    }
}
