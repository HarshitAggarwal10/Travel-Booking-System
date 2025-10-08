package com.apc.user.controller;

import com.apc.common.exception.ValidationException;
import com.apc.common.model.User;
import com.apc.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class UserController {
    private final UserService userService = new UserService();

    public void manageUsers(Scanner sc) {
        while (true) {
            System.out.println("\n--- User Management ---");
            System.out.println("1. Register User");
            System.out.println("2. Login User");
            System.out.println("3. View All Users");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = getInt(sc);
            switch (choice) {
                case 1:
                    registerUser(sc);
                    break;
                case 2:
                    loginUser(sc);
                    break;
                case 3:
                    viewAllUsers();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }

    private void registerUser(Scanner sc) {
        try {
            System.out.print("Enter Username: "); String username = sc.nextLine();
            System.out.print("Enter Password: "); String password = sc.nextLine();
            System.out.print("Enter Email: "); String email = sc.nextLine();
            User u = userService.registerUser(username, password, email);
            System.out.println(u != null ? "✅ User registered successfully! " + u : "❌ Registration failed.");
        } catch (ValidationException vex) {
            System.out.println("Validation error: " + vex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error during registration: " + ex.getMessage());
        }
    }

    private void loginUser(Scanner sc) {
        System.out.print("Enter Username: "); String username = sc.nextLine();
        System.out.print("Enter Password: "); String password = sc.nextLine();
        User user = userService.login(username, password);
        System.out.println(user != null ? "✅ Login Successful!" : "❌ Invalid username or password!");
    }

    private void viewAllUsers() {
        List<User> users = userService.viewAllUsers();
        if (users == null || users.isEmpty()) System.out.println("No users found.");
        else users.forEach(System.out::println);
    }

    private int getInt(Scanner sc) {
        while (!sc.hasNextInt()) { System.out.print("Invalid input, please enter a number: "); sc.next(); }
        int value = sc.nextInt(); sc.nextLine(); return value;
    }
}
