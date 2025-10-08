package com.apc.user.repository;

import com.apc.common.dao.UserDAO;
import com.apc.common.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final UserDAO dao = new UserDAO();

    public User save(User user) {
        return dao.save(user);
    }

    public User findByUsername(String username) {
        return dao.findByUsername(username);
    }

    public List<User> findAll() {
        return dao.findAll();
    }

    public boolean deleteById(int id) {
        return dao.deleteById(id);
    }

    public User findById(int id) {
        return dao.findById(id);
    }
}
