package com.example.publish_service.service;

import com.example.publish_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Date-2/5/2024
 * By Sardor Tokhirov
 * Time-4:09 PM (GMT+5)
 */

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final static String USER_NOT_FOUND="user with %s username or email not found";
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElse(userRepository.findByUserName(username).orElseThrow(()->new UsernameNotFoundException(String.format(USER_NOT_FOUND,username))));
    }
}
