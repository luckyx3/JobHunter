package com.hellcaster.JobHunter.config;

import com.hellcaster.JobHunter.service.SecurityServices.UserDetailedService;
import com.hellcaster.JobHunter.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter{

    private final UserDetailedService userDetailedService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Fetch token from request
        var jwtTokenOptional = getTokenFromRequest(request);
        //Validate jwt token -> JWT utils
        jwtTokenOptional.ifPresent(jwtToken -> {
            if(JwtUtils.validateToken(jwtToken)){
                //get username from jwt token
                var usernameOptional = JwtUtils.getUsernameFromToken(jwtToken);
                usernameOptional.ifPresent(username -> {
                    //fetch userDetails from username
                    var userDetails = userDetailedService.loadUserByUsername(username);

                    //create Authentication token
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    //set authentication token in security context
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                });
            }
        });

        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        var header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(header) && header.startsWith("Bearer ")){
            return Optional.of(header.substring(7));
        }
        return Optional.empty();
    }
}
