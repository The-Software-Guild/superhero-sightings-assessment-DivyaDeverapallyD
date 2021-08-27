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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author DivyaDeverapally
 */
@Repository
public class SuperHeroDaoDBImpl implements SuperHeroDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public void addSuperHero(SuperHero person) {
          final String INSERT_TEACHER = "INSERT INTO super_hero(name, super_power, description) " +
	                "VALUES(?,?,?)";
	        jdbc.update(INSERT_TEACHER,
	               person.getName(),
	               person.getPower(),
	                person.getDescription());
	        
	        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
	        person.setSuperHeroId(newId);
                 insertSuperPersonOrganization(person);
        insertSuperPersonSighting(person);
                
	       // return teacher;

       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     private void insertSuperPersonOrganization(SuperHero superPerson) {
         String q="insert into super_hero_organization (super_hero_id, organization_id)values (?, ?)";
        final int superPersonId = superPerson.getSuperHeroId();
        final List<Organization> org = superPerson.getOrganizations();
        for (Organization currentOrg : org) {
            jdbc.update(q,superPersonId, currentOrg.getOrganizationId());
        }
    }
      private void insertSuperPersonSighting(SuperHero superPerson) {
          String q= "insert into super_hero_sighting (super_hero_id, sighting_id)value(?,?)";
        final int superPersonId = superPerson.getSuperHeroId();
        final List<Sighting> sighting = superPerson.getSightings();

        for (Sighting currentSighting : sighting) {
            jdbc.update(q,
                    superPersonId, currentSighting.getSightingId());
        }
    }

    @Override
    @Transactional
    public void deleteSuperHero(int heroId) {
         jdbc.update("delete from super_hero_organization where super_hero_id = ?", heroId);
        jdbc.update("delete from super_hero_sighting where super_hero_id = ?", heroId);
        jdbc.update("delete from super_hero where super_hero_id = ?", heroId);
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSuperHero(SuperHero person) {
        String q="update super_hero set name = ?, super_power = ? , isHero = ?, description= ? where super_hero_id = ?";
        
             jdbc.update(q,
                person.getName(),
                person.getPower(),
                person.isSuperHeroSide(),
                person.getDescription(),
                person.getSuperHeroId());
       jdbc.update("delete from super_hero_organization where super_hero_id = ?", person.getSuperHeroId());
      jdbc.update("delete from super_hero_organization where organization_id = ? ", person.getSuperHeroId());
        insertSuperPersonOrganization(person);
        insertSuperPersonSighting(person);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SuperHero getSuperHeroById(int superPersonId) {
         try {
            SuperHero superHero= jdbc.queryForObject("select * from super_hero where super_hero_id =?",
                    new SuperPersonMapper(), superPersonId);
            superHero.setSightings(findSightingForSuperPerson(superHero));
            superHero.setOrganizations(findOrganizationForSuperPerson(superHero));

            return superHero;
        } catch (Exception ex) {
            return null;
        }
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SuperHero> getAllSuperHero() {
        //select * from super_person
         List<SuperHero> superPerson = jdbc.query("select * from super_hero",
                new SuperPersonMapper());
        return associateSightingAndOrganizationForSuperPerson(superPerson);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SuperHero> getAllSuperHeroSide(boolean superHero) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SuperHero> getAllSuperHeroByDate(String date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SuperHero> getAllSuperHeroByLocation(Location location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SuperHero> getAllOranizationBySuperPerson(Organization orgId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     private List<SuperHero> associateSightingAndOrganizationForSuperPerson(List<SuperHero> personList) {
        for (SuperHero currentPerson : personList) {
            currentPerson.setSightings(findSightingForSuperPerson(currentPerson));
            currentPerson.setOrganizations(findOrganizationForSuperPerson(currentPerson));
        }
        return personList;
    }
 private List<Sighting> findSightingForSuperPerson(SuperHero superPerson) {
     //check this 
 String SQL_SELECT_SIGHTING_BY_ID = "select * from sighting where sighting_id = ?";

        return jdbc.query(SQL_SELECT_SIGHTING_BY_ID,new SightingMapper(), superPerson.getSightings());

    }
 
  private List<Organization> findOrganizationForSuperPerson(SuperHero superHero) {
 String SQL_SELECT_ALL_SUPER_PERSON_ORGANIZATION = "select * from organization o "
            + "join super_hero_organization spo "
            + "on spo.organization_id = o.organization_id "
            + "where spo.super_hero_id = ? ";
        return jdbc.query(SQL_SELECT_ALL_SUPER_PERSON_ORGANIZATION,//check query
                new OrganizationMapper(), superHero.getSuperHeroId());
    }
  
      private static final class SightingMapper implements org.springframework.jdbc.core.RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {

            Sighting st = new Sighting();
            st.setSightingId(rs.getInt("sighting_id"));
            st.setDescription(rs.getString("description"));
            st.setDate(rs.getTimestamp("date").toLocalDateTime().toLocalDate());

            return st;
        }
    }
      
         private static final class OrganizationMapper implements org.springframework.jdbc.core.RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Organization org = new Organization();
            org.setName(rs.getString("organization_name"));
            org.setPhone(rs.getString("phone"));
            org.setDesciption(rs.getString("description"));
            org.setOrganizationId(rs.getInt("organization_id"));
            return org;
        }
    }
         
          protected static final class SuperPersonMapper implements org.springframework.jdbc.core.RowMapper<SuperHero> {

        @Override
        public SuperHero mapRow(ResultSet rs, int i) throws SQLException {
            SuperHero sp = new SuperHero();
            sp.setName(rs.getString("name"));
            sp.setPower(rs.getString("super_power"));
            sp.setDescription(rs.getString("description"));
            sp.setSuperHeroSide(rs.getBoolean("isHero"));
            sp.setSuperHeroId(rs.getInt("Super_hero_id"));
            return sp;
        }
    }
   
}
