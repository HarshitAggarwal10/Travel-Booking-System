package com.travel.controller;

import com.travel.model.User;
import com.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class UserController {

    private final UserService userService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Main menu for managing users
    public void manageUsers(Scanner scanner) {
        while (true) {
            System.out.println("\n--- User Management ---");
            System.out.println("1. Register User");
            System.out.println("2. Login User");
            System.out.println("3. View All Users");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> registerUser();
                case 2 -> loginUser();
                case 3 -> viewAllUsers();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void registerUser() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        User user = userService.registerUser(username, password, email);

        if (user != null) {
            System.out.println("✅ User registered successfully! " + user);
        } else {
            System.out.println("❌ Registration failed. Username may already exist.");
        }
    }

    private void loginUser() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (userService.login(username, password)) {
            System.out.println("✅ Login Successful!");
        } else {
            System.out.println("❌ Invalid username or password!");
        }
    }

    private void viewAllUsers() {
        List<User> users = userService.viewAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("\n--- All Users ---");
            users.forEach(System.out::println);
        }
    }

    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input, please enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // clear buffer
        return value;
    }
}
