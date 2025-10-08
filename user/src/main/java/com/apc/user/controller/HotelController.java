package com.apc.user.controller;

import com.apc.common.model.Hotel;
import com.apc.common.dao.HotelDAO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class HotelController {
    private final HotelDAO hotelDAO = new HotelDAO();
    private final Scanner scanner = new Scanner(System.in);

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
                case 1:
                    addHotel();
                    break;
                case 2:
                    viewHotels();
                    break;
                case 3:
                    searchHotels();
                    break;
                case 4:
                    deleteHotel();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
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

        Hotel hotel = new Hotel();
        hotel.setName(name); hotel.setLocation(location); hotel.setPricePerNight(price);
        hotel.setAvailableRooms(rooms); hotel.setAmenities(amenities);

        Hotel saved = hotelDAO.save(hotel);
        System.out.println(saved != null ? "✅ Hotel Added Successfully: " + saved : "❌ Hotel not added.");
    }

    private void viewHotels() {
        List<Hotel> hotels = hotelDAO.findAll();
        if (hotels == null || hotels.isEmpty()) System.out.println("No hotels available.");
        else hotels.forEach(System.out::println);
    }

    private void searchHotels() {
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();
        List<Hotel> hotels = hotelDAO.searchByLocation(location);
        if (hotels == null || hotels.isEmpty()) System.out.println("No hotels found at this location.");
        else hotels.forEach(System.out::println);
    }

    private void deleteHotel() {
        System.out.print("Enter Hotel ID to delete: ");
        int id = getIntInput();
        if (hotelDAO.delete(id)) System.out.println("✅ Hotel deleted successfully!");
        else System.out.println("❌ Hotel not found!");
    }

    private int getIntInput() {
        while (!scanner.hasNextInt()) { System.out.print("Invalid input, please enter a number: "); scanner.next(); }
        int value = scanner.nextInt(); scanner.nextLine(); return value;
    }

    private double getDoubleInput() {
        while (!scanner.hasNextDouble()) { System.out.print("Invalid input, please enter a decimal number: "); scanner.next(); }
        double value = scanner.nextDouble(); scanner.nextLine(); return value;
    }
}
