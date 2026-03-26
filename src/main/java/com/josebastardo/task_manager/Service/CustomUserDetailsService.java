package com.josebastardo.task_manager.Service;

import com.josebastardo.task_manager.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (userRepository.existsByUsername(userName)) {
            return userRepository.findByUsername(userName)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        } else {
            return userRepository.findByEmail(userName)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
    }
}
