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
public class LocationDaoDBImplTest {
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperHeroDao heroDao;
    
    public LocationDaoDBImplTest() {
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
    public void testAddGetLocation() {
        Location location = new Location();
        location.setLocationName("Kanata");
        location.setStreet("42 blackdome");
        location.setCity("Kanata");
        location.setState("ON");
        location.setZip(13202);
        location.setLatitude(12);
        location.setLongitude(34);
        locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(fromDao, location);

    }
    
    

   

    /**
     * Test of deleteLocation method, of class LocationDaoDBImpl.
     */
    @Test
    public void testDeleteLocation() {
        Location location = new Location();
       location.setLocationName("Kanata");
        location.setStreet("42 blackdome");
        location.setCity("Kanata");
        location.setState("ON");
        location.setZip(13202);
        location.setLatitude(12);
        location.setLongitude(34);
        locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(fromDao, location);
        
        locationDao.deleteLocation(location.getLocationId());
        assertNull(locationDao.getLocationById(location.getLocationId()));
    }

    /**
     * Test of updateLocation method, of class LocationDaoDBImpl.
     */
    @Test
    public void testUpdateLocation() {
       Location location = new Location();
        location.setLocationName("Kanata");
        location.setStreet("42 blackdome");
        location.setCity("Kanata");
        location.setState("ON");
        location.setZip(13202);
        location.setLatitude(12);
        location.setLongitude(34);
        location.setLongitude(34);
        locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(fromDao, location);
        
        String locationName = "new Kanata";
        Location updateName = locationDao.getLocationById(location.getLocationId());
        updateName.setLocationName(locationName);
        locationDao.addLocation(location);
        
        assertEquals(locationName, updateName.getLocationName());
    }

  

    /**
     * Test of getAllLocation method, of class LocationDaoDBImpl.
     */
    @Test
    public void testGetAllLocation() {
        Location location = new Location();
        location.setLocationName("Kanata");
        location.setStreet("42 blackdome");
        location.setCity("Kanata");
        location.setState("ON");
        location.setZip(13202);
        location.setLatitude(12);
        location.setLongitude(34);
        locationDao.addLocation(location);

        Location location1 = new Location();
        location1.setLocationName("Stitsville");
        location1.setStreet("302 cranesbill");
        location1.setCity("cranesbill");
        location1.setState("ON");
        location1.setZip(1234);
        location1.setLatitude(12);
        location1.setLongitude(21);
        locationDao.addLocation(location1);
        
        List<Location> allLocation = new ArrayList<>();
        allLocation = locationDao.getAllLocation();
        assertEquals(2, allLocation.size());
    }
    
}
