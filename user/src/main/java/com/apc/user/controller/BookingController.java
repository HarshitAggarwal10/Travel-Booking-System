package com.apc.user.controller;

import com.apc.common.model.*;
import com.apc.user.service.BookingService;
import com.apc.user.service.UserService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Component
public class BookingController {
    private final BookingService bookingService = new BookingService();
    private final UserService userService = new UserService();

    public void manageBookings(Scanner sc) {
        while (true) {
            System.out.println("\n--- Booking Management ---");
            System.out.println("1. Book Flight");
            System.out.println("2. Book Hotel");
            System.out.println("3. Cancel Flight Booking");
            System.out.println("4. Cancel Hotel Booking");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = getInt(sc);
            switch (choice) {
                case 1:
                    bookFlight(sc);
                    break;
                case 2:
                    bookHotel(sc);
                    break;
                case 3:
                    cancelFlightBooking(sc);
                    break;
                case 4:
                    cancelHotelBooking(sc);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }

    private void bookFlight(Scanner sc) {
        try {
            System.out.print("Enter User ID: "); int userId = getInt(sc);
            System.out.print("Enter Flight ID: "); int flightId = getInt(sc);
            System.out.print("Enter Number of Passengers: "); int passengers = getInt(sc);

            User u = userService.findById(userId);
            if (u == null) { System.out.println("User not found."); return; }

            FlightBooking confirmed = bookingService.bookFlight(u, flightId, passengers);
            System.out.println(confirmed != null ? "✅ Flight booked successfully: " + confirmed : "❌ Flight booking failed (check seats or IDs).");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void bookHotel(Scanner sc) {
        try {
            System.out.print("Enter User ID: "); int userId = getInt(sc);
            System.out.print("Enter Hotel ID: "); int hotelId = getInt(sc);
            System.out.print("Enter Number of Rooms: "); int rooms = getInt(sc);

            System.out.print("Enter Check-in Date (YYYY-MM-DD): ");
            String inS = sc.nextLine().trim();
            System.out.print("Enter Check-out Date (YYYY-MM-DD): ");
            String outS = sc.nextLine().trim();

            LocalDate checkIn = LocalDate.parse(inS);
            LocalDate checkOut = LocalDate.parse(outS);

            User u = userService.findById(userId);
            if (u == null) { System.out.println("User not found."); return; }

            HotelBooking confirmed = bookingService.bookHotel(u, hotelId, rooms, checkIn, checkOut);
            System.out.println(confirmed != null ? "✅ Hotel booked successfully: " + confirmed : "❌ Hotel booking failed (check availability or dates).");
        } catch (DateTimeParseException dt) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void cancelFlightBooking(Scanner sc) {
        System.out.print("Enter Flight Booking ID to cancel: ");
        int id = getInt(sc);
        System.out.println(bookingService.cancelFlightBooking(id) ? "✅ Flight booking cancelled." : "❌ Flight booking not found.");
    }

    private void cancelHotelBooking(Scanner sc) {
        System.out.print("Enter Hotel Booking ID to cancel: ");
        int id = getInt(sc);
        System.out.println(bookingService.cancelHotelBooking(id) ? "✅ Hotel booking cancelled." : "❌ Hotel booking not found.");
    }

    private int getInt(Scanner sc) {
        while (!sc.hasNextInt()) { System.out.print("Invalid input, please enter a number: "); sc.next(); }
        int v = sc.nextInt(); sc.nextLine(); return v;
    }
}
