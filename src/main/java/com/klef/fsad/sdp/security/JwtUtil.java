package com.klef.fsad.sdp.security;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

@Component
public class JwtUtil {

```
private String SECRET_KEY = "secret123";

public String extractUsername(String token) {
    return extractClaims(token).getSubject();
}

public Date extractExpiration(String token) {
    return extractClaims(token).getExpiration();
}

private Claims extractClaims(String token) {
    return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .getBody();
}

private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
}

public boolean validateToken(String token, UserDetails userDetails) {
    String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
}

public String generateToken(String username) {
    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
}
```

}
