package org.example.carrefour.User.Service;

import org.example.carrefour.Email.Service.EmailService;
import org.example.carrefour.User.Dto.UserRequestDto;
import org.example.carrefour.User.Dto.UserResponseDto;
import org.example.carrefour.User.Entity.User;
import org.example.carrefour.User.Mapper.UserMapper;
import org.example.carrefour.User.Repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper usermapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    EmailService emailService;

    @InjectMocks
    private UserService userService;

    private User userEntity;

    private UserResponseDto userResponseDto;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        // Crear objeto simulado de la entidad
        this.userEntity = new User();
        userEntity.setId(1L);
        userEntity.setEmail("user@example.com");
        userEntity.setUsername("user123");
        userEntity.setPassword("securepassword");

        // Crear objeto simulado de la respuesta DTO
        this.userResponseDto = UserResponseDto.builder()
                .id(1L)
                .email("user@example.com")
                .username("user123")
                .password("securepassword")
                .build();

    }

    @Test
    public void getUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(this.usermapper.mapEntityToDto(userEntity)).thenReturn(userResponseDto);

        UserResponseDto result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals("user@example.com", result.getEmail());
        assertEquals("user123", result.getUsername());

        verify(userRepository, times(1)).findById(1L);
    }







    @AfterEach
    void tearDown(){
        System.out.println("after");
    }
}
