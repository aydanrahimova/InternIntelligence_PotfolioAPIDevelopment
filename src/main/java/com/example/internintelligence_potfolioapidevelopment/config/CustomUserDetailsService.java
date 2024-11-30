package com.example.internintelligence_potfolioapidevelopment.config;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.User;
import com.example.internintelligence_potfolioapidevelopment.dao.repo.UserRepo;
import com.example.internintelligence_potfolioapidevelopment.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found."));
//        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
//        return User.builder()
//                .email(user.getEmail())
//                .password(user.getPassword())
//                .authorities(user.getAuthorities().stream().map(GrantedAuthority::getAuthority))
//                .build();
//
//    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }
}
