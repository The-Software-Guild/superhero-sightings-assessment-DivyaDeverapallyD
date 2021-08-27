/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author DivyaDeverapally
 */
public class Location {
     private int locationId;
    @NotBlank(message = "You must supply a value for locaton name.")
    @Length(max = 50, message = "First Name must be no more than 50 characters in length.")
    private String locationName;
    @NotBlank(message = "You must supply a value for street.")
     private String street;
    @NotBlank(message = "You must supply a value for city.")
    private String city;
    @NotBlank(message = "You must supply a value for state.")
    private String state;
    //@NotEmpty(message = "You must supply a value for zip.")
    private int zip;
    private String description;
    private double longitude;
    private double latitude;
    private List<SuperHero> superPerson = new ArrayList<>();

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.locationId;
        hash = 13 * hash + Objects.hashCode(this.locationName);
        hash = 13 * hash + Objects.hashCode(this.street);
        hash = 13 * hash + Objects.hashCode(this.city);
        hash = 13 * hash + Objects.hashCode(this.state);
        hash = 13 * hash + this.zip;
        hash = 13 * hash + Objects.hashCode(this.description);
        hash = 13 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        hash = 13 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 13 * hash + Objects.hashCode(this.superPerson);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        return true;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public List<SuperHero> getSuperPerson() {
        return superPerson;
    }

    @Override
    public String toString() {
        return "Location{" + "locationId=" + locationId + ", locationName=" + locationName + ", street=" + street + ", city=" + city + ", state=" + state + ", zip=" + zip + ", description=" + description + ", longitude=" + longitude + ", latitude=" + latitude + ", superPerson=" + superPerson + '}';
    }

    public void setSuperPerson(List<SuperHero> superPerson) {
        this.superPerson = superPerson;
    }
    
    
    
    
}
