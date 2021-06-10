package com.example.api.users.common.mapper;

import com.example.api.users.model.UserModelRequest;
import com.example.api.users.model.dto.UserDto;
import com.example.api.users.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapperRequest extends  EntityMapper<UserModelRequest, UserDto>{
    UserModelRequest toDto(UserDto userDto);
    UserDto toEntity(UserModelRequest userModelRequest);
}
