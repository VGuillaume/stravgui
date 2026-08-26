package io.stravgui.infrastructure.adapter.in.security;

import io.stravgui.domain.athlete.AthleteId;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith(BEARER_PREFIX)) {
            String token = header.substring(BEARER_PREFIX.length());
            try {
                AthleteId athleteId = jwtService.validateAndExtractAthleteId(token);
                SecurityContextHolder.getContext().setAuthentication(new AthleteAuthenticationToken(athleteId));
            } catch (InvalidJwtException e) {
                // Token invalide : on ne bloque pas ici, on laisse simplement le contexte
                // non authentifié — c'est la chaîne de filtres Spring Security (authorizeHttpRequests)
                // qui décidera de renvoyer un 401/403 si la route nécessite une authentification
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}