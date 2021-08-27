/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero.dao;

import com.sg.superhero.model.Location;
import com.sg.superhero.model.Organization;
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
public class OrganizationDaoDBImpl  implements OrganizationDao{

    @Autowired
  JdbcTemplate jdbcTemplate;
    
    @Override
    @Transactional
    public void addOrganization(Organization organization) {
        String q="insert into organization(organization_name, phone, description, location_id)values(?,?,?,?)";
         jdbcTemplate.update(q,
                organization.getName(),
                organization.getPhone(),
                organization.getDesciption(),
                organization.getLocation().getLocationId());

        organization.setOrganizationId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));
        inserSupertPersonOrgaization(organization);
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void inserSupertPersonOrgaization(Organization organization) {
        final int organizationId = organization.getOrganizationId();
        String q="insert into super_hero_organization(super_hero_id, organization_id)values(?,?)";
        final List<SuperHero> superPerson = organization.getMembers();
        for (SuperHero currentSuperPerson : superPerson) {
            jdbcTemplate.update(q, currentSuperPerson.getSuperHeroId(), organizationId);
        }
    }

    @Override
    @Transactional
    public void deleteOrganization(int organizationId) {
         jdbcTemplate.update("delete from super_hero_organization where organization_id = ? ", organizationId);
        jdbcTemplate.update("delete from organization where organization_id = ? ", organizationId);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateOrganization(Organization organization) {
        String updateQ="update organization set organization_name = ?, phone = ?, description = ?, location_id = ? where organization_id = ?";
         jdbcTemplate.update(updateQ,
                organization.getName(),
                organization.getPhone(),
                organization.getDesciption(),
                organization.getLocation().getLocationId(),
                organization.getOrganizationId());
        jdbcTemplate.update("delete from super_hero_organization where organization_id = ? ", organization.getOrganizationId());
        inserSupertPersonOrgaization(organization);
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Organization getOrganizationById(int organizationId) {
          try {
            Organization organization = jdbcTemplate.queryForObject("select * from organization where organization_id = ?", new OrganizationMapper(), organizationId);
            organization.setMembers(findSuperPersonFoOrganization(organization));
            organization.setLocation(findLocationForOrganization(organization));
            return organization;
        } catch (Exception ex) {
            return null;
        }
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Organization> getAllSuperPersonByOrganization(SuperHero superHeroId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location getLocationByOrganization(Location location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Organization> getAllOrganization() {
         List<Organization> allOrganizaton = jdbcTemplate.query("select * from organization", new OrganizationMapper());
        return associateLocationAndOrganizationAndSuperPerson(allOrganizaton);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     protected static final class OrganizationMapper implements org.springframework.jdbc.core.RowMapper<Organization> {

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

     
     private List<Organization> associateLocationAndOrganizationAndSuperPerson(List<Organization> organizationList) {
        for (Organization currentOrganization : organizationList) {
            currentOrganization.setMembers(findSuperPersonFoOrganization(currentOrganization));
            currentOrganization.setLocation(findLocationForOrganization(currentOrganization));
        }
        return organizationList;
    }
     
     
    public List<SuperHero> findSuperPersonFoOrganization(Organization organization) {
        String q= "select * from super_hero sp join super_hero_organization spo  on spo.super_hero_id = sp.super_hero_id  where spo.organization_Id = ?";
        return jdbcTemplate.query(q,new SuperHeroMapper(), organization.getOrganizationId());
    }

    public Location findLocationForOrganization(Organization organization) {
        String q=" select * from location l inner join organization o on o.location_id = l.location_id where o.organization_id = ?";
        return jdbcTemplate.queryForObject(q,new LocationMapper(), organization.getOrganizationId());

    }
    
    protected static final class SuperHeroMapper implements org.springframework.jdbc.core.RowMapper<SuperHero> {

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

  protected static final class LocationMapper implements org.springframework.jdbc.core.RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location l = new Location();
            l.setLocationName(rs.getString("location_name"));
            l.setStreet(rs.getString("street"));
            l.setCity(rs.getString("city"));
            l.setState(rs.getString("state"));
            l.setZip(rs.getInt("zip"));
            l.setLatitude(rs.getDouble("latitude"));
            l.setLongitude(rs.getDouble("longitude"));
            l.setDescription(rs.getString("description"));
            l.setLocationId(rs.getInt("location_id"));
            return l;
        }
    }
  
}
