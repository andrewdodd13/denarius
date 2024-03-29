package org.ad13.denarius.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ad13.denarius.model.User;
import org.ad13.denarius.repository.UserRepository;
import org.ad13.denarius.service.exception.UsernameInUseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DenariusUserDetailsService implements UserDetailsService {
    public static final int ROLE_USER = 1, ROLE_ADMIN = 2;

    @Resource
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(arg0);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword()
                .toLowerCase(), getGrantedAuthorities(getRoles(1)));
    }

    public void createUser(String username, String password, String email, String fullname, int role) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            throw new UsernameInUseException(username);
        }

        User newUser = new User(null, username, fullname, email, passwordEncoder.encodePassword(password, username));
        userRepository.save(newUser);
    }

    public User currentUser() {
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public boolean isAdministrator(User user) {
        return false;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    /**
     * Converts an integer role into a list of groups which that role gives the
     * user access to.
     * 
     * @param role
     * @return
     */
    private static List<String> getRoles(int role) {
        List<String> roles = new ArrayList<String>();
        switch (role) {
        case ROLE_ADMIN:
            roles.add("ROLE_ADMIN");
        default:
            roles.add("ROLE_USER");
        }
        return roles;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
