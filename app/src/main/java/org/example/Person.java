package org.example;

import jakarta.persistence.*;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // wird von jpa verwaltet
    private String name;
    private String lastName;
    private String zipcode;
    private String city;
    private Color color;

    public Person() {
        // no-arg constructor damit Hibernate klar kommt
    }

    public Person(String firstName, String lastName, String plz, String city, Color color) {
        this.name = firstName;
        this.lastName = lastName;
        this.zipcode = plz;
        this.city = city;
        this.color = color;
    }

    public Person(String firstName, String lastName, String plz, String city, Long color) {
        this.name = firstName;
        this.lastName = lastName;
        this.zipcode = plz;
        this.city = city;
        this.color = Color.getColorById(color);
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Person name(String name) {
        setName(name);
        return this;
    }

    public Person lastName(String lastName) {
        setLastName(lastName);
        return this;
    }

    public Person zipcode(String zipcode) {
        setZipcode(zipcode);
        return this;
    }

    public Person city(String city) {
        setCity(city);
        return this;
    }

    public Person color(Color color) {
        setColor(color);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, zipcode, city, color);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", lastName='" + getLastName() + "'" +
                ", zipcode='" + getZipcode() + "'" +
                ", city='" + getCity() + "'" +
                ", color='" + getColor() + "'" +
                "}";
    }
}