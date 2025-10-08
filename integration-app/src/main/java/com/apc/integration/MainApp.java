package com.apc.integration;

import com.apc.admin.controller.AdminController;
import com.apc.admin.service.AdminService;
import com.apc.user.service.BookingService;
import com.apc.user.service.UserService;
import com.apc.common.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication(scanBasePackages = "com.apc")
public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MainApp.class, args);

        UserService userService = context.getBean(UserService.class);
        BookingService bookingService = context.getBean(BookingService.class);
        AdminController adminController = context.getBean(AdminController.class);
        AdminService adminService = context.getBean(AdminService.class);

        Scanner sc = new Scanner(System.in);
        final User[] loggedInUser = new User[1];

        while (true) {
            System.out.println("\n===== Travel Booking Console App =====");
            System.out.println("1. Register User");
            System.out.println("2. Login User");
            System.out.println("3. Book Flight");
            System.out.println("4. Book Hotel");
            System.out.println("5. View My Bookings");
            System.out.println("6. Admin Login");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            String line = sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (Exception e) {
                System.out.println("Invalid input. Enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    userService.registerUserConsole(sc);
                    break;
                case 2: {
                    User loggedIn = userService.loginConsole(sc);
                    if (loggedIn != null) {
                        loggedInUser[0] = loggedIn;
                    }
                    break;
                }
                case 3:
                    if (loggedInUser[0] == null) {
                        System.out.println("Please login first.");
                        break;
                    }
                    bookingService.bookFlightConsole(sc, loggedInUser[0]);
                    break;
                case 4:
                    if (loggedInUser[0] == null) {
                        System.out.println("Please login first.");
                        break;
                    }
                    bookingService.bookHotelConsole(sc, loggedInUser[0]);
                    break;
                case 5:
                    if (loggedInUser[0] == null) {
                        System.out.println("Please login first.");
                        break;
                    }
                    System.out.println("Your Flight Bookings:");
                    bookingService.listFlightBookings().stream()
                            .filter(b -> b.getUser().getId().equals(loggedInUser[0].getId()))
                            .forEach(System.out::println);

                    System.out.println("Your Hotel Bookings:");
                    bookingService.listHotelBookings().stream()
                            .filter(b -> b.getUser().getId().equals(loggedInUser[0].getId()))
                            .forEach(System.out::println);
                    break;
                case 6:
                    System.out.print("Enter admin username: ");
                    String u = sc.nextLine().trim();
                    System.out.print("Enter admin password: ");
                    String p = sc.nextLine().trim();
                    if (adminService.login(u, p)) {
                        adminController.adminPanel(sc);
                    } else {
                        System.out.println("Invalid admin credentials!");
                    }
                    break;
                case 7:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }
}
