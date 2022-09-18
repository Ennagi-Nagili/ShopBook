package com.example.test.mapper;

import com.example.test.entity.BookEntity;
import com.example.test.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    Product entityDto(BookEntity bookEntity);

    BookEntity DtoToEntity(Product product);
}
