package com.example.api.users.common.mapper;

import com.example.api.users.model.dto.out.UserDto;
import com.example.api.users.model.entity.UserEntity;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, UserEntity> {
    UserDto toDto(UserEntity userEntity);
    UserEntity toEntity(UserDto userDto);
}