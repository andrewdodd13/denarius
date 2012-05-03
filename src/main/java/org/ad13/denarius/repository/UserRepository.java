package org.ad13.denarius.repository;

import org.ad13.denarius.model.User;

public interface UserRepository {
    public User findByUsername(String username);
    
    public Long createUser(String username, String password, String email, String fullname);
}
