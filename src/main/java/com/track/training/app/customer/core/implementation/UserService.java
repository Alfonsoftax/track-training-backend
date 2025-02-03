package com.track.training.app.customer.core.implementation;
import org.springframework.stereotype.Service;

import com.track.training.app.customer.core.domain.Role;
import com.track.training.app.customer.core.domain.UserDTO;
import com.track.training.app.customer.core.domain.UserRequest;
import com.track.training.app.customer.core.domain.UserResponse;
import com.track.training.app.customer.core.domain.Usuario;
import com.track.training.app.customer.core.outbound.jpa.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository; 

    @Transactional
    public UserResponse updateUser(UserRequest userRequest) {
       
        Usuario user = Usuario.builder()
        .id(userRequest.id)
        .firstname(userRequest.getFirstname())
        .lastname(userRequest.lastname)
        .country(userRequest.getCountry())
        .role(Role.USER)
        .build();
        
        userRepository.save(user);

        return new UserResponse("El usuario se registró satisfactoriamente");
    }

    public UserDTO getUser(String username) {
    	Usuario user= userRepository.findByUsername(username).orElse(null);
       
        if (user!=null)
        {
            UserDTO userDTO = UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .firstname(user.getFirstname())
            .lastname(user.getLastname())
            .country(user.getCountry())
            .build();
            return userDTO;
        }
        return null;
    }
}
