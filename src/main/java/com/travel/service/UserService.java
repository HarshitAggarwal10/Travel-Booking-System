package com.travel.service;

import com.travel.model.User;
import java.util.List;

public interface UserService {
    User registerUser(String username, String password, String email);
    boolean login(String username, String password);
    List<User> viewAllUsers();
}
