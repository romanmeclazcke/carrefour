package org.example.carrefour.User.Service;

import jakarta.persistence.EntityNotFoundException;
import org.example.carrefour.Email.Service.EmailService;
import org.example.carrefour.User.Dto.UserRequestDto;
import org.example.carrefour.User.Dto.UserResponseDto;
import org.example.carrefour.User.Entity.User;
import org.example.carrefour.User.Mapper.UserMapper;
import org.example.carrefour.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Predicate;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    public UserResponseDto getUserById(Long userId) {
        return this.userRepository.findById(userId)
                .map(UserMapper.INSTANCE::mapEntityToDto)
                .orElseThrow(EntityNotFoundException::new);
    }


    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        try{
            User user= UserMapper.INSTANCE.mapDtoToEntity(userRequestDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            this.userRepository.save(user);
            this.emailService.sendEmail(user.getEmail(),"pepe");
            return UserMapper.INSTANCE.mapEntityToDto(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void validateUseEmail(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("User with email " + email + " not found"));
        user.setEmailValidate(true);
        this.userRepository.save(user);
    }
}
