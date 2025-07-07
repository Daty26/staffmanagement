package de.university.staffmanagement.service.impl;

import de.university.staffmanagement.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Service component for generating, parsing, and validating JWT tokens.
 *
 * <p>This class handles:
 * <ul>
 *     <li>Token creation with username and role claims</li>
 *     <li>Extraction of claims such as username and expiration</li>
 *     <li>Token validation against user details</li>
 * </ul>
 *
 * <p>Tokens are signed using HMAC with a static secret key.
 *
 * <p>Expiration is currently set to 20 minutes (1200000 ms).
 *
 * @see io.jsonwebtoken.Jwts
 * @see org.springframework.security.core.userdetails.UserDetails
 */
@Component
public class JwtService {

    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";
    /**
     * Extracts the username (subject) from the provided JWT token.
     *
     * @param token the JWT token
     * @return the username embedded in the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the user's role from the token.
     * Currently retrieves the subject, but the role is actually stored as a claim ("roles").
     *
     * @param token the JWT token
     * @return the role or subject from the token
     */
    public String extractRole(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date of the token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    /**
     * Generic method to extract a specific claim from a JWT token.
     *
     * @param token the JWT token
     * @param claimsResolver a function that takes Claims and returns the desired value
     * @param <T> the type of the extracted claim
     * @return the extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the token after validating the signature.
     *
     * @param token the JWT token
     * @return all claims (payload data) in the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    /**
     * Checks if the token is expired based on its expiration date.
     *
     * @param token the JWT token
     * @return true if expired, false otherwise
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validates a token against a given user.
     * Checks that the username matches and the token is not expired.
     *
     * @param token the JWT token
     * @param userDetails the authenticated user's details
     * @return true if token is valid, false otherwise
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Generates a JWT token with the provided username and role.
     * Adds the role as a "roles" claim in the payload.
     *
     * @param username the username to include
     * @param role the user role
     * @return a signed JWT token
     */
    public String GenerateToken(String username, Role role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", List.of("ROLE_" + role.name()));

        return createToken(claims, username);
    }

    /**
     * Helper method to create a signed token with claims and username.
     * Sets issuance time and expiration.
     *
     * @param claims the claims (e.g., roles)
     * @param username the subject (typically the username)
     * @return a signed JWT token string
     */
    private String createToken(Map<String, Object> claims, String username) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1200000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Decodes the secret key and returns it as a {@link Key} for signing JWTs.
     *
     * @return the signing key
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
