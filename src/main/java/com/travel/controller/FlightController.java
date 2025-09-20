package com.travel.controller;

import com.travel.model.Flight;
import com.travel.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class FlightController {

    private final FlightService flightService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    public void manageFlights(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Flight Management ---");
            System.out.println("1. Add Flight");
            System.out.println("2. View All Flights");
            System.out.println("3. Search Flights");
            System.out.println("4. Delete Flight");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> addFlight();
                case 2 -> viewFlights();
                case 3 -> searchFlights();
                case 4 -> deleteFlight();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void addFlight() {
        System.out.print("Enter Flight Number: ");
        String flightNo = scanner.nextLine();
        System.out.print("Enter Airline: ");
        String airline = scanner.nextLine();
        System.out.print("Enter Origin: ");
        String origin = scanner.nextLine();
        System.out.print("Enter Destination: ");
        String destination = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = getDoubleInput();
        System.out.print("Enter Available Seats: ");
        int seats = getIntInput();

        Flight flight = flightService.createFlight(flightNo, airline, origin, destination, price, seats);
        System.out.println("✅ Flight Added Successfully: " + flight);
    }

    private void viewFlights() {
        List<Flight> flights = flightService.viewAllFlights();
        if (flights.isEmpty()) {
            System.out.println("No flights available.");
        } else {
            flights.forEach(System.out::println);
        }
    }

    private void searchFlights() {
        System.out.print("Enter Origin: ");
        String origin = scanner.nextLine();
        System.out.print("Enter Destination: ");
        String destination = scanner.nextLine();

        List<Flight> flights = flightService.searchFlights(origin, destination);
        if (flights.isEmpty()) {
            System.out.println("No flights found for the given route.");
        } else {
            flights.forEach(System.out::println);
        }
    }

    private void deleteFlight() {
        System.out.print("Enter Flight ID to delete: ");
        int id = getIntInput();
        if (flightService.deleteFlight(id)) {
            System.out.println("✅ Flight deleted successfully!");
        } else {
            System.out.println("❌ Flight not found!");
        }
    }

    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input, please enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private double getDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input, please enter a decimal number: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }
}
