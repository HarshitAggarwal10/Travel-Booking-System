package com.apc.user.controller;

import com.apc.common.model.Flight;
import com.apc.common.dao.FlightDAO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class FlightController {
    private final FlightDAO flightDAO = new FlightDAO();

    public void manageFlights(Scanner sc) {
        while (true) {
            System.out.println("\n--- Flight Management ---");
            System.out.println("1. Add Flight");
            System.out.println("2. View All Flights");
            System.out.println("3. Search Flights");
            System.out.println("4. Delete Flight");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = getInt(sc);
            switch (choice) {
                case 1:
                    addFlight(sc);
                    break;
                case 2:
                    viewFlights();
                    break;
                case 3:
                    searchFlights(sc);
                    break;
                case 4:
                    deleteFlight(sc);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }

    private void addFlight(Scanner sc) {
        try {
            System.out.print("Enter Flight Number: "); String flightNo = sc.nextLine();
            System.out.print("Enter Airline: "); String airline = sc.nextLine();
            System.out.print("Enter Origin: "); String origin = sc.nextLine();
            System.out.print("Enter Destination: "); String destination = sc.nextLine();
            System.out.print("Enter Price: "); double price = getDouble(sc);
            System.out.print("Enter Available Seats: "); int seats = getInt(sc);

            Flight f = new Flight();
            f.setFlightNumber(flightNo); f.setAirline(airline); f.setOrigin(origin); f.setDestination(destination);
            f.setPrice(price); f.setAvailableSeats(seats);

            Flight saved = flightDAO.save(f);
            System.out.println(saved != null ? "✅ Flight Added Successfully: " + saved : "❌ Flight not added (maybe duplicate number).");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void viewFlights() {
        List<Flight> flights = flightDAO.findAll();
        if (flights == null || flights.isEmpty()) System.out.println("No flights available.");
        else flights.forEach(System.out::println);
    }

    private void searchFlights(Scanner sc) {
        System.out.print("Enter Origin: "); String origin = sc.nextLine();
        System.out.print("Enter Destination: "); String destination = sc.nextLine();
        List<Flight> flights = flightDAO.search(origin, destination);
        if (flights == null || flights.isEmpty()) System.out.println("No flights found for the given route.");
        else flights.forEach(System.out::println);
    }

    private void deleteFlight(Scanner sc) {
        System.out.print("Enter Flight ID to delete: ");
        int id = getInt(sc);
        System.out.println(flightDAO.delete(id) ? "✅ Flight deleted successfully!" : "❌ Flight not found!");
    }

    private int getInt(Scanner sc) {
        while (!sc.hasNextInt()) { System.out.print("Invalid input, please enter a number: "); sc.next(); }
        int value = sc.nextInt(); sc.nextLine(); return value;
    }

    private double getDouble(Scanner sc) {
        while (!sc.hasNextDouble()) { System.out.print("Invalid input, please enter a decimal number: "); sc.next(); }
        double value = sc.nextDouble(); sc.nextLine(); return value;
    }
}
