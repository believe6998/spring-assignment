package com.love.start.controller;

import com.love.start.auth.JwtTokenProvider;
import com.love.start.dto.AccountAuthDTO;
import com.love.start.rest.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<Object> auth(@RequestBody AccountAuthDTO accountDTO) {
        Authentication authInfo = new UsernamePasswordAuthenticationToken(
                accountDTO.getUsername(),
                accountDTO.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(authInfo);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User auth = (User) authentication.getPrincipal();

        String jwt = tokenProvider.GenerateToken(new AccountAuthDTO(auth.getUsername(), auth.getPassword()));
        return new ResponseEntity<Object>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value()).setMessage("Ok").setData(jwt).build()
                , HttpStatus.CREATED);
    }

    @GetMapping(value = "/auth/refresh")
    public ResponseEntity<Object> get() {
        return new ResponseEntity<Object>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value()).setMessage("Ok").setData("Refresh token success").build()
                , HttpStatus.CREATED);
    }
}
