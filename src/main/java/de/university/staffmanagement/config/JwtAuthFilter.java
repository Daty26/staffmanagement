package de.university.staffmanagement.config;

import de.university.staffmanagement.entity.PersonalInfo;
import de.university.staffmanagement.service.impl.JwtService;
import de.university.staffmanagement.service.impl.PersonalInfoServiceImpl;
import de.university.staffmanagement.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Security filter for JWT-based authentication.
 *
 * <p>Intercepts every incoming HTTP request and extracts a JWT token from the Authorization header.
 * If valid, the user is authenticated and added to the Spring Security context.
 *
 * <p>Skips filtering for login, token refresh, user registration, and Swagger endpoints.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * Performs JWT validation on incoming requests.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException in case of servlet issues
     * @throws IOException      in case of I/O issues
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }

        filterChain.doFilter(request, response);
    }

    /**
     * Specifies paths that should bypass this filter (e.g. login, Swagger docs).
     *
     * @param request the current HTTP request
     * @return true if the request should skip filtering, false otherwise
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/api/v1/login") ||
                path.equals("/api/v1/refreshToken") ||
                path.equals("/api/v1/users") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs");
    }

}