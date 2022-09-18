package com.example.test.mapper;

import com.example.test.entity.CartEntity;
import com.example.test.entity.FavoriteEntity;
import com.example.test.model.Cart;
import com.example.test.model.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FavoriteMapper {
    FavoriteMapper INSTANCE = Mappers.getMapper(FavoriteMapper.class);

    Favorite entityToDto(FavoriteEntity favoriteEntity);

    FavoriteEntity DtoToEntity(Favorite favorite);
}
