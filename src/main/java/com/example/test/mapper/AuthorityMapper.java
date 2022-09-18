package com.example.test.mapper;

import com.example.test.entity.AuthorityEntity;
import com.example.test.model.Authority;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorityMapper {
    AuthorityMapper INSTANCE = Mappers.getMapper(AuthorityMapper.class);
    Authority entityDto(AuthorityEntity authorityEntity);
    AuthorityEntity DtoToEntity(Authority authority);
}
