package com.polstat.ksa.auth;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil implements Serializable {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Value("${jwt.expiration}")
    private long EXPIRE_DURATION;

    public String generateAccessToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", roles)
                .setIssuer("Polstat")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("JWT expired: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("Token is null, empty or only whitespace: " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            System.out.println("JWT is invalid: " + ex);
        } catch (UnsupportedJwtException ex) {
            System.out.println("JWT is not supported: " + ex);
        } catch (SignatureException ex) {
            System.out.println("Signature validation failed");
        }
        return false;
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public List<String> getRoles(String token) {
        Claims claims = parseClaims(token);
        return claims.get("roles", List.class);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
