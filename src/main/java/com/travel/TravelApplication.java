package com.travel;

import com.travel.controller.BookingController;
import com.travel.controller.FlightController;
import com.travel.controller.HotelController;
import com.travel.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.WebApplicationType;

import java.util.Scanner;

@SpringBootApplication
public class TravelApplication implements CommandLineRunner {

    @Autowired
    private FlightController flightController;

    @Autowired
    private HotelController hotelController;

    @Autowired
    private UserController userController;

    @Autowired
    private BookingController bookingController;

    public static void main(String[] args) {
        // Force Spring Boot to run as a CONSOLE APP, not a web server
        new SpringApplicationBuilder(TravelApplication.class)
                .web(WebApplicationType.NONE) // disable web environment
                .run(args);
    }

    @Override
    public void run(String... args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n==== Travel Booking System ====");
                System.out.println("1. User Management");
                System.out.println("2. Flight Management");
                System.out.println("3. Hotel Management");
                System.out.println("4. Booking Management");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");

                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input! Please enter a number.");
                    continue;
                }

                switch (choice) {
                    case 1 -> userController.manageUsers(scanner);
                    case 2 -> flightController.manageFlights(scanner);
                    case 3 -> hotelController.manageHotels(scanner);
                    case 4 -> bookingController.manageBookings(scanner);
                    case 0 -> {
                        System.out.println("Exiting... Goodbye!");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            }
        }
    }
}
