package com.apc.user.service;

import com.apc.common.exception.ValidationException;
import com.apc.common.model.User;
import com.apc.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository repo = new UserRepository();

    private final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private final Pattern pwdPattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{6,}$");

    public User registerUser(String username, String password, String email) {
        if (username == null || username.length() < 3)
            throw new ValidationException("Username must be >= 3 chars.");
        if (!pwdPattern.matcher(password).matches())
            throw new ValidationException("Password must be >=6 chars, contain upper+lower+digit.");
        if (!emailPattern.matcher(email).matches())
            throw new ValidationException("Invalid email format.");
        if (repo.findByUsername(username) != null)
            throw new ValidationException("Username already exists.");

        User u = new User();
        u.setUsername(username);
        u.setPassword(password); // production: hash
        u.setEmail(email);
        u.setRole("USER");
        return repo.save(u);
    }

    // Console helper
    public void registerUserConsole(Scanner sc) {
        try {
            System.out.print("Enter username: ");
            String username = sc.nextLine().trim();
            if ("admin".equalsIgnoreCase(username)) {
                System.out.println("Cannot register as admin!");
                return;
            }
            System.out.print("Enter password: ");
            String password = sc.nextLine().trim();
            System.out.print("Enter email: ");
            String email = sc.nextLine().trim();
            User u = registerUser(username, password, email);
            System.out.println("? User registered: " + u.getUsername());
        } catch (ValidationException ve) {
            System.out.println("? Error: " + ve.getMessage());
        } catch (Exception ex) {
            System.out.println("? Registration failed: " + ex.getMessage());
        }
    }

    public User login(String username, String password) {
        User u = repo.findByUsername(username);
        if (u == null)
            return null;
        return u.getPassword().equals(password) ? u : null;
    }

    public User loginConsole(Scanner sc) {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User u = repo.findByUsername(username);
        if (u != null && u.getPassword().equals(password)) {
            System.out.println("✅ Login successful. Welcome " + u.getUsername() + "!");
            return u;
        } else {
            System.out.println("❌ Invalid username or password.");
            return null;
        }
    }

    public List<User> viewAllUsers() {
        return repo.findAll();
    }

    public User findById(int id) {
        return repo.findById(id);
    }
}
