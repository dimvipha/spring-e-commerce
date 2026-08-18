package com.vipha.ecommerce.security;



import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class JwtTestController {

    @GetMapping("/security")
    public void getSecurity(Authentication auth) {
        IO.println("Auth: " + auth);
        IO.println("Principal: " + auth.getPrincipal());
        IO.println("Authorities: " + auth.getAuthorities());
    }

    @GetMapping("/jwt")
    public Map<String, Object> getJwt(@AuthenticationPrincipal Jwt jwt) {
        IO.println("Access Token: " + jwt.getTokenValue()); // មើល access token
        IO.println("Keycloak User ID: " + jwt.getSubject()); // មើល subject or userId
        IO.println(jwt.getClaim("name")); // មើលឈ្មោះម្ចាស់ access token

        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        IO.println("Roles: " + realmAccess.get("roles")); // មើល realm roles

        return Map.of(
                "userId", jwt.getSubject(),
                "name", jwt.getClaim("name"),
                "roles", realmAccess.get("roles")
        );
    }

}