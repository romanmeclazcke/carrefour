package org.example.carrefour.User.Service;

import jakarta.persistence.EntityNotFoundException;
import org.example.carrefour.User.Dto.UserRequestDto;
import org.example.carrefour.User.Dto.UserResponseDto;
import org.example.carrefour.User.Entity.User;
import org.example.carrefour.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserResponseDto getUserById(Long userId) {
        return this.userRepository.findById(userId)
                .map(this::mapEntityToDto)
                .orElseThrow(EntityNotFoundException::new);
    }


    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        try{
            User user= this.mapDtoToEntity(userRequestDto);
            this.userRepository.save(user);
            return this.mapEntityToDto(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private UserResponseDto mapEntityToDto(User u){
        UserResponseDto dto = new UserResponseDto();
        dto.setId(u.getId());
        dto.setUsername(u.getUsername());
        dto.setPassword(u.getPassword());
        return dto;
    }


    private User mapDtoToEntity(UserRequestDto u){
        User entity = new User();
        entity.setUsername(u.getUsername());
        entity.setPassword(u.getPassword());
        return entity;
    }
}
