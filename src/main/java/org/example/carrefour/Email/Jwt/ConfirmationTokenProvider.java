    package org.example.carrefour.Email.Jwt;

    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.security.Keys;
    import org.springframework.stereotype.Component;
    import javax.crypto.SecretKey;
    import java.util.Date;

    @Component
    public class ConfirmationTokenProvider {
        private static final String SECRET = "j7ZookpUTYxclaULynjypGQVKMYXqOXMI+/1sQ2gOV1BF6VOHw6OzYj9RNZY4GcHAE3Igrah3MZ26oLrY/3y4Q==";
        private static final SecretKey key;
        private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hours

        static {
            byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(SECRET);
            key = Keys.hmacShaKeyFor(keyBytes);
        }

        public String generateConfirmationToken(String email) {
            try {
                System.out.println("Generating token with email: " + email);
                long now = System.currentTimeMillis();
                Date validity = new Date(now + EXPIRATION_TIME);

                String token = Jwts.builder()
                        .setSubject(email)
                        .setIssuedAt(new Date())
                        .setExpiration(validity)
                        .signWith(key)
                        .compact();

                System.out.println("Token generated: " + token);
                return token;
            } catch (Exception e) {
                e.printStackTrace(); // Mostrar detalles de la excepción
                throw new RuntimeException(e);
            }
        }


        public boolean validateConfirmationToken(String token) {
            try {
                Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public String extractEmailFromToken(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }
    }
