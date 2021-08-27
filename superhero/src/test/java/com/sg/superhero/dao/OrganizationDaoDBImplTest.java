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
public class OrganizationDaoDBImplTest {
    
     @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperHeroDao heroDao;
    
    public OrganizationDaoDBImplTest() {
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
    public void testaddgetOrganization() {

        SuperHero person = new SuperHero();
        person.setName("divya");
        person.setPower("mad");
        person.setSuperHeroSide(true);
        person.setDescription("always mad");
        heroDao.addSuperHero(person);

        List<SuperHero> supersList = new ArrayList<>();
        supersList.add(heroDao.getSuperHeroById(person.getSuperHeroId()));

        Location location = new Location();
      location.setLocationName("Kanata");
        location.setStreet("42 blackdome");
        location.setCity("Kanata");
        location.setState("ON");
        location.setZip(13202);
        location.setLatitude(12);
        location.setLongitude(34);
        locationDao.addLocation(location);

        Organization organization = new Organization();
        organization.setName("test org");
        organization.setPhone("123-122-1232");
        organization.setMembers(supersList);
        organization.setLocation(location);
        organization.setDesciption("test desc");
        organizationDao.addOrganization(organization);
        Organization fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());
        assertEquals(fromDao, organization);

    }
   
  
    /**
     * Test of deleteOrganization method, of class OrganizationDaoDBImpl.
     */
    @Test
    public void testDeleteOrganization() {
        SuperHero person = new SuperHero();
        person.setName("divya");
        person.setPower("mad");
        person.setSuperHeroSide(true);
        person.setDescription("always mad");
        heroDao.addSuperHero(person);

        List<SuperHero> supersList = new ArrayList<>();
        supersList.add(heroDao.getSuperHeroById(person.getSuperHeroId()));

        Location location = new Location();
      location.setLocationName("Kanata");
        location.setStreet("42 blackdome");
        location.setCity("Kanata");
        location.setState("ON");
        location.setZip(13202);
        location.setLatitude(12);
        location.setLongitude(34);
        locationDao.addLocation(location);

        Organization organization = new Organization();
        organization.setName("test org");
        organization.setPhone("123-122-1232");
        organization.setMembers(supersList);
        organization.setLocation(location);
        organization.setDesciption("test desc");
        organizationDao.addOrganization(organization);
         Organization fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());
        assertEquals(fromDao.getOrganizationId(), organization.getOrganizationId());
        organizationDao.deleteOrganization(organization.getOrganizationId());
        assertNull(organizationDao.getOrganizationById(organization.getOrganizationId()));
    }

    /**
     * Test of updateOrganization method, of class OrganizationDaoDBImpl.
     */
    @Test
    public void testUpdateOrganization() {
          SuperHero person = new SuperHero();
        person.setName("divya");
        person.setPower("mad");
        person.setSuperHeroSide(true);
        person.setDescription("always mad");
        heroDao.addSuperHero(person);

        List<SuperHero> supersList = new ArrayList<>();
        supersList.add(heroDao.getSuperHeroById(person.getSuperHeroId()));

        Location location = new Location();
      location.setLocationName("Kanata");
        location.setStreet("42 blackdome");
        location.setCity("Kanata");
        location.setState("ON");
        location.setZip(13202);
        location.setLatitude(12);
        location.setLongitude(34);
        locationDao.addLocation(location);

        Organization organization = new Organization();
        organization.setName("test org");
        organization.setPhone("123-122-1232");
        organization.setMembers(supersList);
        organization.setLocation(location);
        organization.setDesciption("test desc");
        organizationDao.addOrganization(organization);
          String OrganizationName = "new ogg name";
        Organization newName = organizationDao.getOrganizationById(organization.getOrganizationId());
        newName.setName(OrganizationName);
        organizationDao.updateOrganization(newName);
        newName = organizationDao.getOrganizationById(organization.getOrganizationId());
        assertEquals(OrganizationName, newName.getName());
    }

   
  
  

    /**
     * Test of getAllOrganization method, of class OrganizationDaoDBImpl.
     */
    @Test
    public void testGetAllOrganization() {
         SuperHero person = new SuperHero();
        person.setName("divya");
        person.setPower("mad");
        person.setSuperHeroSide(true);
        person.setDescription("always mad");
        heroDao.addSuperHero(person);

        List<SuperHero> supersList = new ArrayList<>();
        supersList.add(heroDao.getSuperHeroById(person.getSuperHeroId()));

        Location location = new Location();
      location.setLocationName("Kanata");
        location.setStreet("42 blackdome");
        location.setCity("Kanata");
        location.setState("ON");
        location.setZip(13202);
        location.setLatitude(12);
        location.setLongitude(34);
        locationDao.addLocation(location);
        Organization organization = new Organization();
        organization.setName("test org");
        organization.setPhone("123-122-1232");
        organization.setMembers(supersList);
        organization.setLocation(location);
        organization.setDesciption("test desc");
        organizationDao.addOrganization(organization);
       
          
          
              Organization organization1 = new Organization();
        organization1.setName("test org1");
        organization1.setPhone("123-122-1632");
        organization1.setMembers(supersList);
        organization1.setLocation(location);
        organization1.setDesciption("test desc1");
        organizationDao.addOrganization(organization1);
        
         List<Organization> allOrgs = new ArrayList<>();
        allOrgs = organizationDao.getAllOrganization();
        assertEquals(2, allOrgs.size());
       
          
    }

   
}
