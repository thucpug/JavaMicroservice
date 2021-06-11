package com.example.api.gateway.security.filter;

import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends BasicAuthenticationFilter {
    private final Environment environment;

    public AuthenticationFilter(AuthenticationManager authenticationManager, Environment environment) {
        super(authenticationManager);
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilterInternal(request, response, chain);
        String authorizationHeader = request.getHeader(environment.getProperty("authorization.token.header.name"));
        if (authorizationHeader == null || !authorizationHeader.startsWith(environment.getProperty("authorization.token.header.prefix"))) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenUserPassword(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        super.doFilterInternal(request,response, chain);
    }

    private UsernamePasswordAuthenticationToken getAuthenUserPassword(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(environment.getProperty("authorization.token.header.name"));
        if (authorizationHeader == null) return null;
        String token = authorizationHeader.replace(environment.getProperty("authorization.token.header.prefix"), "").trim();
        String userId = Jwts.parser()
                .setSigningKey(environment.getProperty("jwt.token.secret"))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        if (userId == null)
            return null;
        return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
    }
}
