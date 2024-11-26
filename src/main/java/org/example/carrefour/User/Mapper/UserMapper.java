package org.example.carrefour.User.Mapper;

import org.example.carrefour.User.Dto.UserRequestDto;
import org.example.carrefour.User.Dto.UserResponseDto;
import org.example.carrefour.User.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponseDto mapEntityToDto(User user);

    User mapDtoToEntity(UserRequestDto userDTO);
}
