package com.lesworks.lesworks_backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1) Si pas d'en-tête Authorization ou ne commence pas par "Bearer ", on laisse passer
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2) On récupère le token sans le "Bearer "
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        // 3) Si on a un email ET qu'aucun user n'est encore authentifié pour cette requête
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // On charge l'utilisateur depuis la base (via UserDetailsService)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 4) On vérifie que le token est valide pour cet utilisateur
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // 5) On crée un objet d'authentification pour Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 6) On enregistre l'utilisateur comme "connecté" pour cette requête
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 7) On continue la chaîne de filtres
        filterChain.doFilter(request, response);
    }
}
