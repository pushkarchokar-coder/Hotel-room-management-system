package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel();

        while (true) {
            System.out.println("\n===== HOTEL MANAGEMENT =====");
            System.out.println("1. Show All Rooms");
            System.out.println("2. Customer Check-in");
            System.out.println("3. Customer Check-out");
            System.out.println("4. Search Customer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                if (!sc.hasNext()) {
                    System.out.println("\nInput closed. Exiting.");
                    break;
                }
                System.out.println("Please enter a valid number.");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    hotel.showRooms();
                    break;

                case 2:
                    hotel.checkIn(sc);
                    break;

                case 3:
                    hotel.checkOut(sc);
                    break;

                case 4:
                    hotel.searchCustomer(sc);
                    break;

                case 5:
                    System.out.println("Thank you for visiting!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }

        sc.close();
    }
}
