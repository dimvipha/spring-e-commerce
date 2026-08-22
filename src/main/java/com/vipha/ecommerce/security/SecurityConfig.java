package com.vipha.ecommerce.security;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

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
                                        "/scalar/**",
                                        "/api/v1/auth/register"
                                        )
                                .permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/v1/categories/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/v1/categories/**").hasAnyAuthority("STAFF","ADMIN")
//                                .requestMatchers(HttpMethod.DELETE,"/api/v1/categories/**").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/api/v1/categories/**").hasAnyAuthority("STAFF","ADMIN")
                                .requestMatchers(HttpMethod.PATCH,"/api/v1/categories/**").hasAnyAuthority("STAFF","ADMIN")
                                // =========================
                                // Tags
                                // =========================
                                .requestMatchers(HttpMethod.GET, "/api/v1/tags/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/tags/**")
                                .hasAnyAuthority("STAFF", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/tags/**")
                                .hasAnyAuthority("STAFF", "ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/tags/**")
                                .hasAnyAuthority("STAFF", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/tags/**")
                                .hasAuthority("ADMIN")

                                // =========================
                                // Products
                                // =========================
                                .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/products/**")
                                .hasAnyAuthority("STAFF", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/products/**")
                                .hasAnyAuthority("STAFF", "ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/products/**")
                                .hasAnyAuthority("STAFF", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**")
                                .hasAuthority("ADMIN")

                                // =========================
                                // Orders
                                // =========================
                                .requestMatchers(HttpMethod.POST, "/api/v1/orders/**")
                                .hasAuthority("CUSTOMER")
                                // Customer's own orders
                                .requestMatchers(HttpMethod.GET, "/api/v1/orders/me")
                                .hasAuthority("CUSTOMER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/orders/**")
                                .hasAnyAuthority( "STAFF", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/orders/**")
                                .hasAnyAuthority("CUSTOMER", "STAFF", "ADMIN")
//                                .requestMatchers(HttpMethod.PATCH, "/api/v1/orders/**")
//                                .hasAnyAuthority("STAFF","CUSTOMER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/**")
                                .hasAuthority("ADMIN")

                                // =========================
                                // Medias
                                // =========================
                                .requestMatchers(HttpMethod.GET, "/api/v1/medias/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/medias/**")
                                .hasAnyAuthority("STAFF", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/medias/**")
                                .hasAnyAuthority("STAFF", "ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/medias/**")
                                .hasAnyAuthority("STAFF", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/medias/**")
                                .hasAuthority("ADMIN")
                                .anyRequest()
                                .authenticated());
                // 3. Authentication Mechanism
//        http.httpBasic(Customizer.withDefaults());
        http.oauth2ResourceServer(
                oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
        );

//        http.oauth2ResourceServer(oauth2->oauth2.jwt(Customizer.withDefaults()));

        // 4. Disable CSRF (for post-endpoint requests because of stateless security)
        http.csrf(AbstractHttpConfigurer::disable);


         return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        Converter<Jwt, Collection<GrantedAuthority>> converter = jwt -> {
            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
            Collection<String> roles = new HashSet<>();
            if (realmAccess != null) {
                roles = realmAccess.get("roles");
            }
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toSet());
        };

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(converter);

        return jwtAuthenticationConverter;
    }

}
