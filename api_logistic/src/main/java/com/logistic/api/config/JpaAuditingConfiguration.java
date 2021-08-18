/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logistic.api.config;

import com.logistic.api.model.User;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author PROSIA
 */
@Component
public class JpaAuditingConfiguration implements AuditorAware<String>{

    @Override
    public Optional<String> getCurrentAuditor() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        
        if(authentication == null) {
            return null;
        }
        
        Object principal = authentication.getPrincipal();
        
        if(principal instanceof User) {
            return Optional.of(((User) principal).getUsername());
        }
        
        if(principal instanceof String && !principal.equals("anonymousUser")) {
            return Optional.of((String) principal);
        }
        
        return Optional.of("System");
    }
    
}
