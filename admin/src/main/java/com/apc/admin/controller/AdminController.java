package com.apc.admin.controller;

import com.apc.admin.service.AdminService;
import com.apc.common.model.Flight;
import com.apc.common.model.Hotel;
import com.apc.common.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // show console admin panel (called from MainApp)
    public void adminPanel(Scanner sc) {
        System.out.println("\n??? Admin Panel");
        while (true) {
            System.out.println("\n1. View All Users");
            System.out.println("2. Manage Flights");
            System.out.println("3. Manage Hotels");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter choice: ");
            int c;
            try {
                c = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (c) {
                case 1:
                    viewUsers();
                    break;
                case 2:
                    manageFlights(sc);
                    break;
                case 3:
                    manageHotels(sc);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    private void viewUsers() {
        List<User> users = adminService.getAllUsers();
        if (users == null || users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        users.forEach(u -> System.out.println(u.getId() + " - " + u.getUsername() + " (" + u.getEmail() + ")"));
    }

    private void manageFlights(Scanner sc) {
        while (true) {
            System.out.println("\n-- Flights --");
            System.out.println("1. List Flights");
            System.out.println("2. Add Flight");
            System.out.println("3. Update Flight");
            System.out.println("4. Delete Flight");
            System.out.println("5. Back");
            System.out.print("Choice: ");
            int ch;
            try { ch = Integer.parseInt(sc.nextLine()); } catch (Exception e) { System.out.println("Invalid"); continue; }

            switch (ch) {
                case 1:
                    listFlights();
                    break;
                case 2:
                    addFlightConsole(sc);
                    break;
                case 3:
                    updateFlightConsole(sc);
                    break;
                case 4:
                    deleteFlightConsole(sc);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid");
                    break;
            }
        }
    }

    private void listFlights() {
        List<Flight> flights = adminService.getAllFlights();
        if (flights == null || flights.isEmpty()) {
            System.out.println("No flights found.");
            return;
        }
        flights.forEach(f -> System.out.println(f.getId() + " - " + f.getFlightNumber() + " " + f.getOrigin() + "->" + f.getDestination() + " seats:" + f.getAvailableSeats() + " price:" + f.getPrice()));
    }

    private void addFlightConsole(Scanner sc) {
        Flight f = new Flight();
        System.out.print("Flight number: "); f.setFlightNumber(sc.nextLine().trim());
        System.out.print("Airline: "); f.setAirline(sc.nextLine().trim());
        System.out.print("Origin: "); f.setOrigin(sc.nextLine().trim());
        System.out.print("Destination: "); f.setDestination(sc.nextLine().trim());
        System.out.print("Available seats: "); f.setAvailableSeats(Integer.parseInt(sc.nextLine()));
        System.out.print("Price: "); f.setPrice(Double.parseDouble(sc.nextLine()));
        Flight saved = adminService.addFlight(f);
        if (saved != null) System.out.println("Flight added id=" + saved.getId());
        else System.out.println("Failed to add flight. Check input & uniqueness.");
    }

    private void updateFlightConsole(Scanner sc) {
        System.out.print("Enter flight id to update: ");
        int id = Integer.parseInt(sc.nextLine());
        List<Flight> flights = adminService.getAllFlights();
        Flight existing = flights.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        if (existing == null) { System.out.println("Flight not found"); return; }
        System.out.print("New Price (current " + existing.getPrice() + "): ");
        existing.setPrice(Double.parseDouble(sc.nextLine()));
        System.out.print("New seats (current " + existing.getAvailableSeats() + "): ");
        existing.setAvailableSeats(Integer.parseInt(sc.nextLine()));
        Flight u = adminService.updateFlight(existing);
        if (u != null) System.out.println("Updated.");
        else System.out.println("Update failed.");
    }

    private void deleteFlightConsole(Scanner sc) {
        System.out.print("Flight id to delete: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean ok = adminService.deleteFlight(id);
        System.out.println(ok ? "Deleted" : "Not found / cannot delete");
    }

    private void manageHotels(Scanner sc) {
        while (true) {
            System.out.println("\n-- Hotels --");
            System.out.println("1. List Hotels");
            System.out.println("2. Add Hotel");
            System.out.println("3. Update Hotel");
            System.out.println("4. Delete Hotel");
            System.out.println("5. Back");
            System.out.print("Choice: ");
            int ch;
            try { ch = Integer.parseInt(sc.nextLine()); } catch (Exception e) { System.out.println("Invalid"); continue; }

            switch (ch) {
                case 1:
                    listHotels();
                    break;
                case 2:
                    addHotelConsole(sc);
                    break;
                case 3:
                    updateHotelConsole(sc);
                    break;
                case 4:
                    deleteHotelConsole(sc);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid");
                    break;
            }
        }
    }

    private void listHotels() {
        List<Hotel> hotels = adminService.getAllHotels();
        if (hotels == null || hotels.isEmpty()) {
            System.out.println("No hotels found.");
            return;
        }
        hotels.forEach(h -> System.out.println(h.getId() + " - " + h.getName() + " @" + h.getLocation() + " rooms:" + h.getAvailableRooms() + " price:" + h.getPricePerNight()));
    }

    private void addHotelConsole(Scanner sc) {
        Hotel h = new Hotel();
        System.out.print("Name: "); h.setName(sc.nextLine().trim());
        System.out.print("Location: "); h.setLocation(sc.nextLine().trim());
        System.out.print("Available rooms: "); h.setAvailableRooms(Integer.parseInt(sc.nextLine()));
        System.out.print("Price per night: "); h.setPricePerNight(Double.parseDouble(sc.nextLine()));
        System.out.print("Amenities (comma): "); h.setAmenities(sc.nextLine().trim());
        Hotel saved = adminService.addHotel(h);
        if (saved != null) System.out.println("Hotel added id=" + saved.getId());
        else System.out.println("Failed to add hotel.");
    }

    private void updateHotelConsole(Scanner sc) {
        System.out.print("Hotel id to update: ");
        int id = Integer.parseInt(sc.nextLine());
        List<Hotel> hotels = adminService.getAllHotels();
        Hotel existing = hotels.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        if (existing == null) { System.out.println("Hotel not found"); return; }
        System.out.print("New price per night (current " + existing.getPricePerNight() + "): ");
        existing.setPricePerNight(Double.parseDouble(sc.nextLine()));
        System.out.print("New available rooms (current " + existing.getAvailableRooms() + "): ");
        existing.setAvailableRooms(Integer.parseInt(sc.nextLine()));
        Hotel u = adminService.updateHotel(existing);
        System.out.println(u != null ? "Updated." : "Update failed.");
    }

    private void deleteHotelConsole(Scanner sc) {
        System.out.print("Hotel id to delete: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean ok = adminService.deleteHotel(id);
        System.out.println(ok ? "Deleted" : "Not found / cannot delete");
    }
}
