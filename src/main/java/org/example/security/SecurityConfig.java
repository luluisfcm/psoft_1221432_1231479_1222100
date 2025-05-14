package org.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class SecurityConfig {

    // Define um utilizador admin em memória
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin1234")
                .roles("ADMIN")
                .build();

        UserDetails maria = User.withDefaultPasswordEncoder()
                .username("maria")
                .password("maria123")
                .roles("PATIENT")
                .build();

        UserDetails nelson = User.withDefaultPasswordEncoder()
                .username("nelson")
                .password("nelson123")
                .roles("PHYSICIAN")
                .build();

        return new InMemoryUserDetailsManager(admin, maria, nelson);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());

        return http.build();
    }
}
