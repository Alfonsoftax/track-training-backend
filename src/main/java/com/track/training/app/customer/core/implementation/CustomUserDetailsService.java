//package com.track.training.app.customer.core.implementation;
//
//import java.util.Collections;
//
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.track.training.app.customer.core.domain.Usuario;
//import com.track.training.app.customer.core.outbound.jpa.UserRepository;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Usuario user = userRepository.findByUsername(username).get();
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), 
//                Collections.singletonList(new SimpleGrantedAuthority(user.getRoles())));
//    }
//}
//package com;


