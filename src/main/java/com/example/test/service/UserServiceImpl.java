package com.example.test.service;

import com.example.test.entity.AuthorityEntity;
import com.example.test.entity.UserEntity;
import com.example.test.repository.AuthorityRepository;
import com.example.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        AuthorityEntity authorityEntity = authorityRepository.findByUsername(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        return new User(userEntity.getUsername(), userEntity.getPassword(), rolesToAuthorities(authorityEntity.getAuthority()));
    }

    private Collection<? extends GrantedAuthority> rolesToAuthorities(String role) {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }
}
