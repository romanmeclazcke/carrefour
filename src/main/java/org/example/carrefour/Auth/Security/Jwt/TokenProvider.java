package org.example.carrefour.Auth.Security.Jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private static final String SECRET = "j7ZookpUTYxclaULynjypGQVKMYXqOXMI+/1sQ2gOV1BF6VOHw6OzYj9RNZY4GcHAE3Igrah3MZ26oLrY/3y4Q==";
    private static final String AUTHORITIES_KEY = "auth";
    private static final SecretKey key;
    private final JwtParser jwtParser;
    private final int tokenValidityInMilliseconds;

    static {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenProvider() {
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidityInMilliseconds = 1000 * 86400; // Token válido por 1 día.
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);


        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key)
                .setExpiration(validity)
                .setIssuedAt(new Date())
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            final var claims = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);
            this.checkTokenExpiration(claims);
            return true;
        } catch (JwtException e) {
            throw  new JwtException("Invalid Token");
        }
    }

    private void checkTokenExpiration(Jws<Claims> token) {
        try {
            final var payload = token.getBody();
            if (payload.getExpiration().before(new Date()) || payload.getIssuedAt().after(new Date((new Date()).getTime() + this.tokenValidityInMilliseconds))) {
                throw new ExpiredJwtException(null, null, null);
            }
        } catch (Exception e) {
            throw new ExpiredJwtException(null, null, null);
        }
    }
}
