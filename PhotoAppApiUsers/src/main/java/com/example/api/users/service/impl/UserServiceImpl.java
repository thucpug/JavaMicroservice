package com.example.api.users.service.impl;

import com.example.api.users.common.mapper.UserMapper;
import com.example.api.users.model.dto.in.UserLoginDtoIn;
import com.example.api.users.model.dto.out.UserDto;
import com.example.api.users.model.entity.UserEntity;
import com.example.api.users.repository.UserRepository;
import com.example.api.users.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
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

    @Override
    public UserDto login(UserLoginDtoIn userLoginDtoIn) {
        return null;
    }

    @Override
    public UserDto loadUserDetailsByEmail(String username) {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null) throw new UsernameNotFoundException(username);
        return new ModelMapper().map(userEntity,UserDto.class);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(s);
        if (userEntity == null) throw new UsernameNotFoundException(s);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                true, true, true, true, new ArrayList<>());
    }
}
