package com.logistic.api.service;

import com.logistic.api.model.User;
import com.logistic.api.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(readOnly = false, rollbackFor = {Exception.class})
public class UserService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        User user = userRepo.findByUsername(username);
        log.debug("User : {}", user);
        if (user == null) {
            log.warn("Username : {} not found.", username);
            throw new UsernameNotFoundException("Username : " + username + " not found.");
        }
        return user;
    }

    public void updateTokenUser(String token, String username) {
        User user = userRepo.findByUsername(username);
        user.setToken(token);
        userRepo.save(user);
    }

    public void saveUser(User user) {
        try {
            userRepo.save(user);
        } catch (Exception e) {
            // TODO: handle exception
            log.debug("error save user : {}", e.getCause());
        }
    }

    public boolean isUserExist(String username) {
        return userRepo.existsByUsername(username);
    }

}
