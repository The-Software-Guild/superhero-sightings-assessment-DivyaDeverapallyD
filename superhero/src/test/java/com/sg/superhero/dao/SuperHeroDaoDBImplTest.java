/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero.dao;

import com.sg.superhero.model.Location;
import com.sg.superhero.model.Organization;
import com.sg.superhero.model.Sighting;
import com.sg.superhero.model.SuperHero;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author DivyaDeverapally
 */
@SpringBootTest
public class SuperHeroDaoDBImplTest {
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperHeroDao heroDao;
    
    public SuperHeroDaoDBImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
         List<Organization> organization = organizationDao.getAllOrganization();
        for (Organization currentOrganization : organization) {
            organizationDao.deleteOrganization(currentOrganization.getOrganizationId());
        }
        List<SuperHero> superPerson = heroDao.getAllSuperHero();
        for (SuperHero currentPerson : superPerson) {
            heroDao.deleteSuperHero(currentPerson.getSuperHeroId());
        }
        List<Sighting> sighting = sightingDao.getAllSighting();
        for (Sighting currentSighting : sighting) {
            sightingDao.deletSigting(currentSighting.getSightingId());
        }
        List<Location> location = locationDao.getAllLocation();
        for (Location currentlLocation : location) {
            locationDao.deleteLocation(currentlLocation.getLocationId());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

      @Test
    public void testAddGetSuperPerson() {
   
        SuperHero hero = new SuperHero();
        hero.setName("divya");
        hero.setPower("mad");
        hero.setSuperHeroSide(true);
        hero.setDescription("madgirl");
        
        List<Organization> organizations = new ArrayList<>();
        hero.setOrganizations(organizations);
        
        List<Sighting> sightings = new ArrayList<>();
        hero.setSightings(sightings);
        
        heroDao.addSuperHero(hero);
        SuperHero fromDao =heroDao.getSuperHeroById(hero.getSuperHeroId());
        assertEquals(fromDao, hero);

    }
    
    
    
    
    
    /**
     * Test of deleteSuperHero method, of class SuperHeroDaoDBImpl.
     */
    @Test
    public void testDeleteSuperHero() {
         SuperHero hero = new SuperHero();
        hero.setName("divya");
        hero.setPower("mad");
        hero.setSuperHeroSide(true);
        hero.setDescription("madgirl");

        List<Organization> organizations = new ArrayList<>();
        hero.setOrganizations(organizations);

        
        List<Sighting> sightings = new ArrayList<>();
        hero.setSightings(sightings);
        heroDao.addSuperHero(hero);

        SuperHero fromDao = heroDao.getSuperHeroById(hero.getSuperHeroId());

        assertEquals(fromDao, hero);
        heroDao.deleteSuperHero(hero.getSuperHeroId());
        assertNull(heroDao.getSuperHeroById(hero.getSuperHeroId()));
    }

    /**
     * Test of updateSuperHero method, of class SuperHeroDaoDBImpl.
     */
    @Test
    public void testUpdateSuperHero() {
          SuperHero hero = new SuperHero();
        hero.setName("divya");
        hero.setPower("mad");
        hero.setSuperHeroSide(true);
        hero.setDescription("madgirl");

        List<Organization> organizations = new ArrayList<>();
        hero.setOrganizations(organizations);

        
        List<Sighting> sightings = new ArrayList<>();
        hero.setSightings(sightings);
        heroDao.addSuperHero(hero);
        
        String name="new mad";
         SuperHero updateName = heroDao.getSuperHeroById(hero.getSuperHeroId());
        updateName.setName(name);
        heroDao.updateSuperHero(updateName);
        updateName = heroDao.getSuperHeroById(hero.getSuperHeroId());

        assertEquals(name, updateName.getName());

    }

  

    /**
     * Test of getAllSuperHero method, of class SuperHeroDaoDBImpl.
     */
    @Test
    public void testGetAllSuperHero() {
         SuperHero person = new SuperHero();
        person.setName("divya");
        person.setPower("mad");
        person.setSuperHeroSide(true);
        person.setDescription("madgirl");

        List<Organization> organizations = new ArrayList<>();
        person.setOrganizations(organizations);

        List<Sighting> sightings = new ArrayList<>();
        person.setSightings(sightings);
        heroDao.addSuperHero(person);

        SuperHero person1 = new SuperHero();
        person1.setName("sandy");
        person1.setPower("anger");
        person1.setSuperHeroSide(true);
        person1.setDescription("angry man");

        List<Organization> organization1 = new ArrayList<>();
        person1.setOrganizations(organizations);

        List<Sighting> sighting1 = new ArrayList<>();
        person1.setSightings(sightings);
        heroDao.addSuperHero(person1);

        List<SuperHero> supersList = new ArrayList<>();
        supersList = heroDao.getAllSuperHero();
        assertEquals(2, supersList.size());
    }

  
    
}
