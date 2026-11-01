
package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Hotel {
    private final HashMap<Integer, Room> rooms = new HashMap<>();
    private final HashMap<Integer, Customer> bookings = new HashMap<>();
    private final ArrayList<Customer> customerList = new ArrayList<>();

    // ai
    private static final String FILE = "data/hotel_data.txt";

    public Hotel() {
        // Create 5 rooms
        for (int i = 101; i <= 105; i++) {
            rooms.put(i, new Room(i, 1500));
        }

        loadData();
    }

    public void showRooms() {
        for (Room room : new TreeMap<>(rooms).values()) {
            room.displayRoom();
        }
    }
// ai
    public void checkIn(Scanner sc) {
        System.out.print("Enter room number: ");
        Integer roomNo = readInteger(sc);
        if (roomNo == null) return; 

        Room room = rooms.get(roomNo);

        if (room == null) {
            System.out.println("Room does not exist!");
            return;
        }

        if (!room.isAvailable()) {
            System.out.println("Room is already occupied!");
            return;
        }

        System.out.print("Customer name: ");
        String name = sc.nextLine();

        System.out.print("Phone number: ");
        String phone = sc.nextLine();

        System.out.print("Number of days: ");
        Integer daysInput = readInteger(sc);
        if (daysInput == null) return;
        int days = daysInput;

        if (name.trim().isEmpty() || phone.trim().isEmpty() || days <= 0
                || name.contains("|") || phone.contains("|")
                || name.contains("\n") || phone.contains("\n")) {
            System.out.println("Invalid customer details!");
            return;
        }

        Customer customer = new Customer(name, phone, days);

        room.setAvailable(false);
        bookings.put(roomNo, customer);
        customerList.add(customer);

        saveData();

        System.out.println("Check-in successful!");
        System.out.println("Estimated bill: Rs. " +
                           (room.getPrice() * days));
    }

    public void checkOut(Scanner sc) {
        System.out.print("Enter room number: ");
        Integer roomNoInput = readInteger(sc);
        if (roomNoInput == null) return;
        int roomNo = roomNoInput;

        Room room = rooms.get(roomNo);
        Customer customer = bookings.get(roomNo);

        if (room == null || customer == null) {
            System.out.println("No active booking found!");
            return;
        }

        double bill = room.getPrice() * customer.getDays();

        System.out.println("Customer: " + customer.getName());
        System.out.println("Total bill: Rs. " + bill);

        room.setAvailable(true);
        bookings.remove(roomNo);
        customerList.remove(customer);

        saveData();

        System.out.println("Check-out successful!");
    }

    public void searchCustomer(Scanner sc) {
        System.out.print("Enter room number: ");
        Integer roomNo = readInteger(sc);
        if (roomNo == null) return;

        Customer customer = bookings.get(roomNo);

        if (customer == null) {
            System.out.println("No customer in this room.");
        } else {
            customer.displayCustomer();
        }
    }

    private void saveData() {
        try {
            File file = new File(FILE);
            File parent = file.getParentFile();
// ai
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                throw new IOException("Could not create directory " + parent);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Map.Entry<Integer, Customer> entry
                        : bookings.entrySet()) {

                    Customer c = entry.getValue();

                    writer.write(entry.getKey() + "|" +
                        c.getName() + "|" +
                        c.getPhone() + "|" +
                        c.getDays());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Could not save data: " +
                               e.getMessage());
        }
    }

    private void loadData() {
        File file = new File(FILE);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                 new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|", -1);

                if (data.length != 4) {
                    continue;
                }

                try {
                    int roomNo = Integer.parseInt(data[0]);
                    String name = data[1].trim();
                    String phone = data[2].trim();
                    int days = Integer.parseInt(data[3]);

                    Room room = rooms.get(roomNo);

                    if (room != null && room.isAvailable()
                            && !name.trim().isEmpty()
                            && !phone.trim().isEmpty()
                            && days > 0) {

                        Customer c =
                            new Customer(name, phone, days);

                        room.setAvailable(false);
                        bookings.put(roomNo, c);
                        customerList.add(c);
                    } else {
                        System.out.println("Skipping invalid or duplicate booking record.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(
                        "Skipping invalid booking record.");
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load data: " +
                               e.getMessage());
        }
    }

    private Integer readInteger(Scanner sc) {
        while (true) {
            if (sc.hasNextInt()) {
                int value = sc.nextInt();
                sc.nextLine();
                return value;
            }
            if (!sc.hasNext()) {
                System.out.println("Input closed; operation cancelled.");
                return null;
            }
            System.out.println("Please enter a valid whole number.");
            sc.nextLine();
        }
    }
}
