package org.example.carrefour.Product.Mapper;

import org.example.carrefour.Product.Dto.ProductRequestDto;
import org.example.carrefour.Product.Dto.ProductResponseDto;
import org.example.carrefour.Product.Entity.Product;
import org.example.carrefour.User.Mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponseDto mapEntityToDto(Product product);
    Product mapDtoToEntity(ProductRequestDto ProductRequestDto);

}
