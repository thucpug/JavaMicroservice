package com.example.api.users.security;

import com.example.api.users.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.InetAddress;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final Environment environment;
    private final UserService userService;

    public WebSecurity(Environment environment, @Lazy UserService userService) {
        this.environment = environment;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        String ip = InetAddress.getLoopbackAddress().getHostAddress();
        http.csrf().disable();
        http.authorizeRequests()
               // .antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"))
                .antMatchers("/api/user/create").permitAll()
                .antMatchers("/h2-console/**").permitAll()
               // .antMatchers("/api/user/user").permitAll()
               // .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .addFilter(getAuthenticationFilter());
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment, passwordEncoder(),authenticationManager());
        //authenticationFilter.setAuthenticationManager(authenticationManager());
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
        return authenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}




