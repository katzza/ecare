package com.ecare.services;

import com.ecare.dao.UserDAO;
import com.ecare.domain.UserEntity;
import com.ecare.domain.UserRole;
import com.ecare.error.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserDAO userDao;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<UserEntity> foundByEmail = userDao.findByEmail(s);
        if (foundByEmail.size() == 0) {
            log.info("User not found by name: " + s);
            throw new UsernameNotFoundException("User not found by name: " + s);
        } else {
            UserEntity user = userDao.findByEmail(s).get(0);
            List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        }
    }

    private User buildUserForAuthentication(UserEntity
                                                    user, List<GrantedAuthority> authorities) {
        return new User(user.getEmail(), user.getPassword(), authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
        Set<GrantedAuthority> setAuths = new HashSet<>();
        for (UserRole role : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(role.toString()));
        }
        return new ArrayList<>(setAuths);
    }

    @ExceptionHandler(UsernameNotFoundException.class) //todo move to controller
    public String handleException() {
        return "/error/usernotfound";
    }
}
