package com.love.start.service;

import com.love.start.entity.Account;
import com.love.start.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountAuthService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        Account account  = accountOptional.orElse(null);
        if (account == null) {
            throw new UsernameNotFoundException("Not fond username: " + username);
        }
        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles("USER")
                .build();
    }

    public UserDetails loadUserByID(Long id) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findById(id);
        Account account  = accountOptional.orElse(null);
        if (account == null) {
            throw new UsernameNotFoundException("Not fond id: " + id);
        }
        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles("USER")
                .build();
    }
}
