/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero.dao;

import com.sg.superhero.model.Location;
import com.sg.superhero.model.Organization;
import com.sg.superhero.model.SuperHero;
import java.util.List;

/**
 *
 * @author DivyaDeverapally
 */
public interface OrganizationDao {
     public void addOrganization(Organization organization);
    
    public void deleteOrganization(int organizationId);
    
    public void updateOrganization(Organization organization);
    
    public Organization getOrganizationById(int organizationId);
    
    public List<Organization> getAllSuperPersonByOrganization(SuperHero superHeroId);
    
    public Location getLocationByOrganization(Location location);
    
    public List<Organization> getAllOrganization();
}
