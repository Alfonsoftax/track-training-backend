package com.track.training.app.customer.core.implementation;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.track.training.app.customer.core.domain.AuthResponse;
import com.track.training.app.customer.core.domain.LoginRequest;
import com.track.training.app.customer.core.domain.RegisterRequest;
import com.track.training.app.customer.core.domain.Role;
import com.track.training.app.customer.core.domain.Usuario;
import com.track.training.app.customer.core.outbound.jpa.UserRepository;
import com.track.training.app.customer.customer.adapters.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername())
        	    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .username(user.getUsername())
            .build();

    }

    public AuthResponse register(RegisterRequest request) {
        Usuario user = Usuario.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode( request.getPassword()))
            .firstname(request.getFirstname())
            .lastname(request.lastname)
            .country(request.getCountry())
            .role(Role.USER)
            .build();

        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
        
    }

}