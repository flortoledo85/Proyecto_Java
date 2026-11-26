
package com.talentotech.optica_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/pedidos", "/productos/**"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/productos", "/productos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/pedidos").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/productos/**").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }
}
// package com.talentotech.optica_backend.config;

// import java.beans.BeanProperty;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.config.Customizer;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// public class SecurityConfig {

// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http)
// throws Exception{

// http.cors(Customizer.withDefaults()).authorizeHttpRequests(auth ->
// auth.requestMatchers(HttpMethod.GET, "/productos",
// "/productos/**").permitAll().anyRequest().authenticated());

// return http.build();
// }
// }
