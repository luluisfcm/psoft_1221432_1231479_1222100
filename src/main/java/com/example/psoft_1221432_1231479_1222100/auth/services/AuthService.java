package com.example.psoft_1221432_1231479_1222100.auth.services;

import com.example.psoft_1221432_1231479_1222100.usermanagement.model.User;
import com.example.psoft_1221432_1231479_1222100.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import java.time.Instant;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;

    public Authentication authenticate(String username, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        long expiry = 3600L;

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String role = user.getRole().name();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("psoft-api")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("roles", "ROLE_" + user.getRole().name()) // exemplo: "ROLE_ADMIN"
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
