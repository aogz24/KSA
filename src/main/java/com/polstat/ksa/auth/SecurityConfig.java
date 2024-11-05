package com.polstat.ksa.auth;

import com.polstat.ksa.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired private JwtFilter jwtTokenFilter;
    private final CustomUserDetailsService userDetailsService;
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(antMatcher(HttpMethod.POST, "/register")).permitAll()
                .requestMatchers(antMatcher(HttpMethod.POST, "/login")).permitAll()
                .requestMatchers(antMatcher(HttpMethod.GET, "/profiles")).permitAll()
                .requestMatchers(antMatcher(HttpMethod.POST, "/kabupaten/create")).hasRole("ADMIN")
                .requestMatchers(antMatcher(HttpMethod.POST, "/provinsi/create")).hasRole("ADMIN")
                .requestMatchers(antMatcher(HttpMethod.PUT, "/update-role/**")).hasRole("ADMIN")
                .requestMatchers(antMatcher(HttpMethod.PUT, "/kabupaten/update/**")).hasRole("ADMIN")
                .requestMatchers(antMatcher(HttpMethod.PUT, "/kabupaten/delete/**")).hasRole("ADMIN")
                .requestMatchers(antMatcher(HttpMethod.PUT, "/provinsi/update/**")).hasRole("ADMIN")
                .requestMatchers(antMatcher("/ksa/**")).hasAnyRole("ADMIN", "PEGAWAI")
                .requestMatchers(antMatcher("/ksa/informasiksa/create")).hasRole("ADMIN")
                .anyRequest().authenticated()
        );
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

