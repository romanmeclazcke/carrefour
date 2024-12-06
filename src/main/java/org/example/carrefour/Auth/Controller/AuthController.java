package org.example.carrefour.Auth.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.carrefour.Auth.Dto.LoginDto;
import org.example.carrefour.Auth.Security.Jwt.TokenProvider;
import org.example.carrefour.Email.Jwt.ConfirmationTokenProvider;
import org.example.carrefour.User.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenProvider confirmationTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = "Bearer " + tokenProvider.createToken(authentication);
        return ResponseEntity.status(HttpStatus.OK).body("Token" + token);
    }


    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String token) {
        Boolean isValid = this.confirmationTokenProvider.validateConfirmationToken(token);
        if (isValid) {
            String email = this.confirmationTokenProvider.extractEmailFromToken(token);
            return ResponseEntity.status(HttpStatus.OK).body("Account successfully confirmed for email: " + email);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
    }

}
