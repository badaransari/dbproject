package com.example.javafx_db_example;

public class Person {
    private String name;
    private String email;
    private String address;
    private String password;
    private String phone;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Person(String name, String email, String address, String password, String phone) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
        this.phone = phone;
    }
}
