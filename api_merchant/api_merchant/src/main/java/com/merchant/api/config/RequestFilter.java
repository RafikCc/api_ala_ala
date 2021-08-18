package com.merchant.api.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.merchant.api.service.UserService;
import com.merchant.api.util.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class RequestFilter extends OncePerRequestFilter {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenUtil tokenUtil;

	@Override
    protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response, FilterChain filter) throws ServletException, IOException {
        final String tokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            jwtToken = tokenHeader.substring(7);
            
            try {
                username = tokenUtil.getUsernameFromToken(jwtToken);
                Date expirationDate = tokenUtil.getExpirationDateFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                log.debug("Unable to get JWT Token for {}", username);
            } catch (ExpiredJwtException eje) {
                log.debug("JWT Token has expired for {}", username);
            }
        } else {
            log.warn("JWT Token is null");
        }
        
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userService.loadUserByUsername(username);
            
            if(tokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                        new UsernamePasswordAuthenticationToken(userDetails, 
                                null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        
        filter.doFilter(request, response);
    }
}
