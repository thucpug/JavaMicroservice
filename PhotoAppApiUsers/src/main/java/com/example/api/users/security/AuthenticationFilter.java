package com.example.api.users.security;

import com.example.api.users.model.dto.in.UserLoginDtoIn;
import com.example.api.users.model.dto.out.UserDto;
import com.example.api.users.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private UserService userService;
    private final Environment environment;
    private  final PasswordEncoder passwordEncoder;

    public AuthenticationFilter(UserService userService,
                                Environment environment,
                                PasswordEncoder passwordEncoder,
                                AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.environment = environment;
        this.passwordEncoder = passwordEncoder;
        super.setAuthenticationManager(authenticationManager);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            Object temp = request.getInputStream();
            UserLoginDtoIn creds = new ObjectMapper()
                    .readValue(request.getInputStream(), UserLoginDtoIn.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String userName = ((User) authResult.getPrincipal()).getUsername();
        UserDto userDto = userService.loadUserDetailsByEmail(userName);
        String token = io.jsonwebtoken.Jwts.builder()
                .setSubject(userDto.getUserId())
                .setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(environment.getProperty("jwt.token.expiration"))))
                .signWith(SignatureAlgorithm.HS512,environment.getProperty("jwt.token.secret"))
                .compact();
        response.addHeader("authrization",token);
        response.addHeader("userId",userDto.getUserId());
    }
}
