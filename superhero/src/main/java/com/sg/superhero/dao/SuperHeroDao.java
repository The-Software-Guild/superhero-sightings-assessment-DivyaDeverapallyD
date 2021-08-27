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
public interface SuperHeroDao {
      public void addSuperHero(SuperHero person);
    
    public void deleteSuperHero(int personId);
    
    public void updateSuperHero(SuperHero person);
    
    public SuperHero getSuperHeroById(int superPersonId);
    
    public List<SuperHero> getAllSuperHero();
    
    public List<SuperHero> getAllSuperHeroSide(boolean superHero);
    
    public List<SuperHero> getAllSuperHeroByDate(String date);
            
    public List<SuperHero> getAllSuperHeroByLocation(Location location); 
    
    public List<SuperHero> getAllOranizationBySuperPerson(Organization orgId);
    
}
