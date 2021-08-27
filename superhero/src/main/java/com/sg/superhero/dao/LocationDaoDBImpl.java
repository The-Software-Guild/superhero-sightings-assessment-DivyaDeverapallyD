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
public class LocationDaoDBImpl implements  LocationDao{

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Override
    public void addLocation(Location location) {
        String s="insert into location (location_name , street, city, state, zip, latitude,longitude, description )values(?,?,?,?,?,?,?,?)";
          jdbcTemplate.update(s,
                location.getLocationName(),
                location.getStreet(),
                location.getCity(),
                location.getState(),
                location.getZip(),
                location.getLatitude(),
                location.getLongitude(),
                location.getDescription());
        location.setLocationId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));
      insertSuperPersonIntoLcaiton(location);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteLocation(int locationId) {
         List<Sighting> sightingList = jdbcTemplate.query("select s.*from sighting s inner join location l on l.location_id = s.location_id where l.location_id = ? ", new SightingMapper(), locationId);
    
    List<Organization> orginazationList = jdbcTemplate.query("select o.* from organization o  inner join location l on l.location_id = o.location_id  where l.location_id= ?", new OrganizationMapper(), locationId);
    for(Sighting sighting: sightingList){
        jdbcTemplate.update("delete from super_hero_sighting where sighting_id = ?", sighting.getSightingId()); 
        
   }  
    for(Organization organization: orginazationList){
        jdbcTemplate.update(
"delete from super_hero_organization where organization_id = ? ", organization.getOrganizationId());
    }
      jdbcTemplate.update("delete from sighting where location_id= ?", locationId);
      jdbcTemplate.update("delete from organization where location_id = ? ", locationId);
      jdbcTemplate.update("delete from location where location_id = ? ", locationId); 
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateLocation(Location location) {
         jdbcTemplate.update("update location set   location_name = ? , street = ?, city = ?, state = ?, zip = ?, latitude = ?,longitude = ?, description = ?  where location_id =?",
                location.getLocationName(),
                location.getStreet(),
                location.getCity(),
                location.getState(),
                location.getZip(),
                location.getLatitude(),
                location.getLongitude(),
                location.getDescription(),
                location.getLocationId());
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location getLocationById(int locationId) {
          try {
            Location location = jdbcTemplate.queryForObject("select * from location where location_id = ?", new LocationMapper(), locationId);
            location.setSuperPerson(findSuperPersonForlocation(location));
            return location;
        } catch (Exception ex) {
            return null;
        }
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Location> getAllLocation() {
        List<Location> locations = jdbcTemplate.query("select * from location", new LocationMapper());
        return locations;
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     private  List<SuperHero> findSuperPersonForlocation(Location location){
      String s="select distinct sp.*  from location l  inner join sighting s on l.location_id = s.location_id  inner join super_hero_sighting sps on s.Sighting_id = sps.Sighting_id  inner join super_hero sp on sps.super_hero_id = sp.Super_hero_id   where l.location_id = ?";
        return jdbcTemplate.query(s,
                new SuperPersonMapper(), location.getLocationId());
    }
    
      private void insertSuperPersonIntoLcaiton(Location location){
        final int locationId= location.getLocationId();
        List<SuperHero> supersList = location.getSuperPerson();
        
        for(SuperHero currentSuper: supersList){
            jdbcTemplate.update("insert into super_hero (name, super_power, isHero, description) values(?, ?, ?, ?)", currentSuper.getSuperHeroId(), locationId);
        }
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
