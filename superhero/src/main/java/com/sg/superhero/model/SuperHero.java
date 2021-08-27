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
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author DivyaDeverapally
 */
public class SuperHero {

    private int superHeroId;

    @NotBlank(message = "First name must not be empty.")
    @Length(max = 50, message = "Power Name must be no more than 50 characters in length.")
    private String power;
    @NotBlank(message = "First name must not be empty.")
    @Length(max = 50, message = " Name must be no more than 50 characters in length.")
    private String name;
    private boolean superHeroSide;
    private String description;

    private List<Sighting> sightings = new ArrayList<>();
    private List<Organization> organizations = new ArrayList<>();
    
     @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.superHeroId;
        hash = 53 * hash + Objects.hashCode(this.power);
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + (this.superHeroSide ? 1 : 0);
        hash = 53 * hash + Objects.hashCode(this.description);
        hash = 53 * hash + Objects.hashCode(this.sightings);
        hash = 53 * hash + Objects.hashCode(this.organizations);
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
        final SuperHero other = (SuperHero) obj;
        return true;
    }

    public int getSuperHeroId() {
        return superHeroId;
    }

    public void setSuperHeroId(int superHeroId) {
        this.superHeroId = superHeroId;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSuperHeroSide() {
        return superHeroSide;
    }

    public void setSuperHeroSide(boolean superHeroSide) {
        this.superHeroSide = superHeroSide;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Sighting> getSightings() {
        return sightings;
    }

    public void setSightings(List<Sighting> sightings) {
        this.sightings = sightings;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }
}
