package com.krzxs.wypozyczalnia.config;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers("/login").permitAll()
                        // Kierownik
                        .requestMatchers(HttpMethod.GET, "/flota/nowy").hasRole("KIEROWNIK")
                        .requestMatchers(HttpMethod.GET, "/flota/*/edycja").hasRole("KIEROWNIK")
                        .requestMatchers(HttpMethod.POST, "/flota/*/serwis").hasRole("KIEROWNIK")
                        .requestMatchers(HttpMethod.POST, "/flota/*/powrot").hasRole("KIEROWNIK")
                        .requestMatchers(HttpMethod.POST, "/flota/*/wycofaj").hasRole("KIEROWNIK")
                        .requestMatchers(HttpMethod.POST, "/flota/*/usun").hasRole("KIEROWNIK")
                        .requestMatchers(HttpMethod.POST, "/flota/*").hasRole("KIEROWNIK")
                        .requestMatchers(HttpMethod.POST, "/flota").hasRole("KIEROWNIK")
                        .requestMatchers("/cennik/**").hasRole("KIEROWNIK")
                        // Konsultant
                        .requestMatchers(HttpMethod.GET, "/wypozyczenie/nowe").hasRole("KONSULTANT")
                        .requestMatchers(HttpMethod.POST, "/wypozyczenie").hasRole("KONSULTANT")
                        .requestMatchers(HttpMethod.POST, "/wypozyczenie/*/zwrot").hasRole("KONSULTANT")
                        .requestMatchers("/rezerwacja/**").hasRole("KONSULTANT")
                        .requestMatchers("/klienci/rejestracja").hasRole("KONSULTANT")
                        // Reszta
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/flota")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?wylogowano")
                        .permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()))
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        return http.build();
    }
}
