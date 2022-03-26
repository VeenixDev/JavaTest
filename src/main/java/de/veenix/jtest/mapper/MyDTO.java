package de.veenix.jtest.mapper;

public class MyDTO {

    private String name;
    private String lastName;

    public MyDTO(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public MyDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return lastName;
    }

    public void setSurName(String surName) {
        this.lastName = surName;
    }

    @Override
    public String toString() {
        return "MyDTO{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
