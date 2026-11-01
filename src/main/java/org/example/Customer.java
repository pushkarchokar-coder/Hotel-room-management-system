
package org.example;

public class Customer {
    private String name;
    private String phone;
    private int days;

    public Customer(String name, String phone, int days) {
        this.name = name;
        this.phone = phone;
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getDays() {
        return days;
    }

    public void displayCustomer() {
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phone);
        System.out.println("Days: " + days);
    }
}
