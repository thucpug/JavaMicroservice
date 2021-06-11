package com.example.api.users.common.mapper;

import com.example.api.users.model.dto.in.UserSignInDtoIn;
import com.example.api.users.model.dto.out.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapperRequest extends  EntityMapper<UserSignInDtoIn, UserDto>{
    UserSignInDtoIn toDto(UserDto userDto);
    UserDto toEntity(UserSignInDtoIn userSignInDtoIn);
}
