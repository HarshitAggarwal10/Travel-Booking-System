package com.travel.controller;

import com.travel.model.Hotel;
import com.travel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class HotelController {

    private final HotelService hotelService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    public void manageHotels(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Hotel Management ---");
            System.out.println("1. Add Hotel");
            System.out.println("2. View All Hotels");
            System.out.println("3. Search Hotels by Location");
            System.out.println("4. Delete Hotel");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> addHotel();
                case 2 -> viewHotels();
                case 3 -> searchHotels();
                case 4 -> deleteHotel();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void addHotel() {
        System.out.print("Enter Hotel Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();
        System.out.print("Enter Price per Night: ");
        double price = getDoubleInput();
        System.out.print("Enter Available Rooms: ");
        int rooms = getIntInput();
        System.out.print("Enter Amenities: ");
        String amenities = scanner.nextLine();

        Hotel hotel = hotelService.createHotel(name, location, price, rooms, amenities);
        System.out.println("✅ Hotel Added Successfully: " + hotel);
    }

    private void viewHotels() {
        List<Hotel> hotels = hotelService.viewAllHotels();
        if (hotels.isEmpty()) {
            System.out.println("No hotels available.");
        } else {
            hotels.forEach(System.out::println);
        }
    }

    private void searchHotels() {
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();
        List<Hotel> hotels = hotelService.searchHotels(location);

        if (hotels.isEmpty()) {
            System.out.println("No hotels found at this location.");
        } else {
            hotels.forEach(System.out::println);
        }
    }

    private void deleteHotel() {
        System.out.print("Enter Hotel ID to delete: ");
        int id = getIntInput();
        if (hotelService.deleteHotel(id)) {
            System.out.println("✅ Hotel deleted successfully!");
        } else {
            System.out.println("❌ Hotel not found!");
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
