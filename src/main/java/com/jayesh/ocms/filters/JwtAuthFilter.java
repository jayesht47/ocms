package com.jayesh.ocms.filters;

import com.jayesh.ocms.entities.User;
import com.jayesh.ocms.exceptions.NotFoundException;
import com.jayesh.ocms.services.JwtService;
import com.jayesh.ocms.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserService userService;

    private final Logger customLogger = LoggerFactory.getLogger(JwtAuthFilter.class);

    public JwtAuthFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getJwtTokenFromRequest(request);

        if (token != null && !jwtService.isTokenExpired(token)) {
            String userName = jwtService.getUserNameFromToken(token);
            try {
                User user = userService.getUserByUserName(userName);
                List<String> roles = user.getRoles();
                Collection<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .toList();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName, null
                        , authorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
                customLogger.info("Set auth context for user : {}", userName);
            } catch (NotFoundException e) {
                customLogger.error("User with username {} not found", userName);
            }

        } else {
            customLogger.warn("request does not contain auth token!");
        }

        filterChain.doFilter(request, response);

    }

    private String getJwtTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            return token.substring(7);
        }
        return null;
    }
}
