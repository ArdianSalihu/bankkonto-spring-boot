package com.ardian.bankkonto_spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Diese Klasse enthält zentrale Sicherheits-Einstellungen für die Anwendung
public class SecurityConfig {

    // Erstellt ein Werkzeug, das Passwörter mit dem BCrypt-Algorithmus hasht und vergleicht
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Sagt Spring Security, wie ein Nutzer anhand seines Usernamens in der Datenbank gefunden wird
    @Bean
    public UserDetailsService userDetailsService(NutzerRepository nutzerRepository) {
        return username -> {
            Nutzer nutzer = nutzerRepository.findByUsername(username);
            if (nutzer == null) {
                throw new UsernameNotFoundException("Nutzer nicht gefunden");
            }
            // Baut ein spezielles Spring-Security-User-Objekt aus den eigenen Nutzerdaten
            return User.builder()
                    .username(nutzer.getUsername())
                    .password(nutzer.getPassword())
                    .build();
        };
    }

    // Legt fest, welche Anfragen geschützt werden und wie (hier: alle Anfragen brauchen ein Login)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF-Schutz ist für Browser-Formulare gedacht, bei einer REST-API würde er POST/PUT/DELETE blockieren
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/registrieren").permitAll() // Diese eine Adresse darf jeder aufrufen, ohne Login
                        .anyRequest().authenticated() // Jede Anfrage muss authentifiziert sein
                )
                .httpBasic(Customizer.withDefaults()); // Nutzt einfache Basic-Auth (Username/Passwort-Login)


        return http.build();
    }
}
