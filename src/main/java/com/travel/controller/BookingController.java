package com.travel.controller;

import com.travel.model.FlightBooking;
import com.travel.model.HotelBooking;
import com.travel.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Component
public class BookingController {

    private final BookingService bookingService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void manageBookings(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Booking Management ---");
            System.out.println("1. Book Flight");
            System.out.println("2. Book Hotel");
            System.out.println("3. Cancel Flight Booking");
            System.out.println("4. Cancel Hotel Booking");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> bookFlight();
                case 2 -> bookHotel();
                case 3 -> cancelFlightBooking();
                case 4 -> cancelHotelBooking();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void bookFlight() {
        System.out.print("Enter User ID: ");
        int userId = getIntInput();
        System.out.print("Enter Flight ID: ");
        int flightId = getIntInput();
        System.out.print("Enter Number of Passengers: ");
        int passengers = getIntInput();

        FlightBooking confirmed = bookingService.bookFlight(userId, flightId, passengers);

        if (confirmed != null) {
            System.out.println("✅ Flight booked successfully: " + confirmed);
        } else {
            System.out.println("❌ Flight booking failed.");
        }
    }

    private void bookHotel() {
        System.out.print("Enter User ID: ");
        int userId = getIntInput();
        System.out.print("Enter Hotel ID: ");
        int hotelId = getIntInput();
        System.out.print("Enter Number of Rooms: ");
        int rooms = getIntInput();

        LocalDate checkIn;
        LocalDate checkOut;
        try {
            System.out.print("Enter Check-in Date (YYYY-MM-DD): ");
            checkIn = LocalDate.parse(scanner.nextLine().trim());
            System.out.print("Enter Check-out Date (YYYY-MM-DD): ");
            checkOut = LocalDate.parse(scanner.nextLine().trim());
        } catch (DateTimeParseException ex) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
            return;
        }

        if (!checkOut.isAfter(checkIn)) {
            System.out.println("Check-out must be after check-in.");
            return;
        }

        HotelBooking confirmed = bookingService.bookHotel(userId, hotelId, rooms, checkIn, checkOut);

        if (confirmed != null) {
            System.out.println("✅ Hotel booked successfully: " + confirmed);
        } else {
            System.out.println("❌ Hotel booking failed.");
        }
    }

    private void cancelFlightBooking() {
        System.out.print("Enter Flight Booking ID to cancel: ");
        int id = getIntInput();

        if (bookingService.cancelFlightBooking(id)) {
            System.out.println("✅ Flight booking cancelled.");
        } else {
            System.out.println("❌ Flight booking not found.");
        }
    }

    private void cancelHotelBooking() {
        System.out.print("Enter Hotel Booking ID to cancel: ");
        int id = getIntInput();

        if (bookingService.cancelHotelBooking(id)) {
            System.out.println("✅ Hotel booking cancelled.");
        } else {
            System.out.println("❌ Hotel booking not found.");
        }
    }

    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input, please enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // clear newline
        return value;
    }
}
