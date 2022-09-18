package com.example.test.mapper;

import com.example.test.entity.UserEntity;
import com.example.test.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User entityDto(UserEntity userEntity);

    UserEntity DtoToEntity(User user);
}
