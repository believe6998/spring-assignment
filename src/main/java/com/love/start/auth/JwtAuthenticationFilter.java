package com.love.start.auth;

import com.love.start.config.WebSecurityConfig;
import com.love.start.service.AccountAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(WebSecurityConfig.class.getName());

    @Value("${jwt.secret.key}")
    private String JWT_SECRET_KEY;
    @Value("${jwt.secret.prefix}")
    private String JWT_SECRET_PREFIX;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AccountAuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(req);

            if (StringUtils.hasText(jwt) && tokenProvider.ValidateToken(jwt)) {
                String username = tokenProvider.GetUserNameFromJWT(jwt);

                UserDetails userDetails = authService.loadUserByUsername(username);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ex) {
            LOGGER.warning("failed on set user authentication: " + ex.toString());
        }

        filterChain.doFilter(req, resp);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(JWT_SECRET_KEY);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JWT_SECRET_PREFIX)) {
            return bearerToken.substring(6);
        }
        return null;
    }
}
