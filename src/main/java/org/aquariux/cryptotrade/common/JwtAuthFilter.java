package org.aquariux.cryptotrade.common;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.aquariux.cryptotrade.constant.ApplicationConstant;
import org.aquariux.cryptotrade.entity.UserEntity;
import org.aquariux.cryptotrade.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(ApplicationConstant.AUTHORIZATION_HEADER);

        if(StringUtils.isNotBlank(header) && header.startsWith(ApplicationConstant.BEARER_PREFIX)) {
            String token = header.substring(ApplicationConstant.BEARER_PREFIX.length());
            if(jwtUtils.isValid(token)) {
                String username = jwtUtils.extractUsername(token);
                UserEntity user = userService.findUserByUsername(username);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null);

                SecurityContextHolder.getContext().setAuthentication(auth);

            }
        }
        filterChain.doFilter(request, response);
    }
}
