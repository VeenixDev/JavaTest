package de.veenix.jtest.mapper;

import java.util.UUID;

public class MyEntity {

    @MapperOptions(ignore = true)
    private UUID id;
    private String name;
    @MapperOptions(renameTo = "lastName")
    private String surName;

    public MyEntity(UUID id, String name, String surName) {
        this.id = id;
        this.name = name;
        this.surName = surName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    @Override
    public String toString() {
        return "MyEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                '}';
    }
}
