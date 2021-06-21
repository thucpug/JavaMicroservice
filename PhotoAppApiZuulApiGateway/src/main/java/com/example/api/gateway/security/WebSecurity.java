package com.example.api.gateway.security;

import com.example.api.gateway.security.filter.AuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.ws.rs.HttpMethod;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final Environment environment;

    public WebSecurity(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                .antMatchers(environment.getProperty("api.h2console.url")).permitAll()
               // .antMatchers("/hi").permitAll()
                .antMatchers(HttpMethod.POST, environment.getProperty("api.login.url")).permitAll()
                .antMatchers(HttpMethod.POST, environment.getProperty("api.register.url")).permitAll()
//               .antMatchers("/userservice/api/user/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager(), environment));

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

}
