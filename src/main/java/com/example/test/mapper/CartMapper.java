package com.example.test.mapper;

import com.example.test.entity.CartEntity;
import com.example.test.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    Cart entityToDto(CartEntity cartEntity);

    CartEntity DtoToEntity(Cart cart);
}