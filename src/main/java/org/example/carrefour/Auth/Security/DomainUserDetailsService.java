package org.example.carrefour.Auth.Security;

import org.example.carrefour.User.Entity.User;
import org.example.carrefour.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DomainUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con email " + userEmail + " no existe"));
    }

}
