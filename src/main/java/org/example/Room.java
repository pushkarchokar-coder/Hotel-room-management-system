
package org.example;

public class Room {
    private int roomNumber;
    private double price;
    private boolean available;

    public Room(int roomNumber, double price) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.available = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void displayRoom() {
        System.out.println(
            "Room: " + roomNumber +
            " | Price: Rs. " + price +
            " | Status: " +
            (available ? "Available" : "Occupied")
        );
    }
}
