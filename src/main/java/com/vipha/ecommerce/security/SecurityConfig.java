package com.vipha.ecommerce.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        // Todo authentication in memory
        // 1. Rest Architecture -Stateless Security
                http
                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 2. Endpoints Policy
                http.authorizeHttpRequests(request->
                        request
                                .requestMatchers(
                                        "/swagger-ui.html/**",
                                        "/swagger-ui/**",
                                        "/v3/scalar.js",
                                        "/v3/api-docs/**",
                                        "/scalar/**"
                                        )
                                .permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/v1/categories/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/v1/categories/**").hasAnyRole("STAFF","ADMIN")
//                                .requestMatchers(HttpMethod.DELETE,"/api/v1/categories/**").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/api/v1/categories/**").hasAnyRole("STAFF","ADMIN")
                                .requestMatchers(HttpMethod.PATCH,"/api/v1/categories/**").hasAnyRole("STAFF","ADMIN")
                                .anyRequest()
                                .authenticated());
                // 3. Authentication Mechanism
        http.httpBasic(Customizer.withDefaults());

        // 4. Disable CSRF (for post-endpoint requests because of stateless security)
        http.csrf(AbstractHttpConfigurer::disable);


         return http.build();
    }
}
