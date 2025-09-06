package com.hotel.management;

public class Customer {
    private int customerId;
    private String name;
    private String email;
    private String phone;
    private String address;

    // For inserts (without id)
    public Customer(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // For reads/updates (with id)
    public Customer(int customerId, String name, String email, String phone, String address) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int getCustomerId() { return customerId; }
    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public String getPhone()    { return phone; }
    public String getAddress()  { return address; }

    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setName(String name)          { this.name = name; }
    public void setEmail(String email)        { this.email = email; }
    public void setPhone(String phone)        { this.phone = phone; }
    public void setAddress(String address)    { this.address = address; }
}
