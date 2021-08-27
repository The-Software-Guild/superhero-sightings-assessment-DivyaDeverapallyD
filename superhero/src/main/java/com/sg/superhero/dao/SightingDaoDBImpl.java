/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero.dao;

import com.sg.superhero.model.Location;
import com.sg.superhero.model.Sighting;
import com.sg.superhero.model.SuperHero;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class SightingDaoDBImpl implements SightingDao{
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void addSighting(Sighting sighting) {
        String insertQuery="insert into sighting(date, description, location_id )values(?, ?, ?)";
         jdbcTemplate.update(insertQuery,
                sighting.getDate().toString(),
                sighting.getDescription(),
                sighting.getLocation().getLocationId());
        sighting.setSightingId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));
        insertSupePersonForSighting(sighting);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 private void insertSupePersonForSighting(Sighting sighting) {
     String s= "insert into super_hero_sighting(super_hero_id, sighting_id )values(?,?)";
        final int sightingId = sighting.getSightingId();
        final List<SuperHero> superPerson = sighting.getSuperPerson();

        for (SuperHero currentPerson : superPerson) {
            jdbcTemplate.update(s,currentPerson.getSuperHeroId(), sightingId);
        }
    }
    @Override
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deletSigting(int sightingId) {
        jdbcTemplate.update(
"delete from super_hero_sighting where sighting_id = ?", sightingId);
        jdbcTemplate.update("delete from sighting where location_id= ?", sightingId);
        jdbcTemplate.update("delete from sighting where sighting_id = ? ", sightingId);

    }

    @Override
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSighting(Sighting sighting) {
                jdbcTemplate.update("update sighting set date = ?, description = ?, location_id = ? where sighting_id = ?",
                sighting.getDate().toString(),
                sighting.getDescription(),
                sighting.getLocation().getLocationId(),
                sighting.getSightingId());
        jdbcTemplate.update("delete from super_hero_sighting where sighting_id = ?", sighting.getSightingId());
        insertSupePersonForSighting(sighting);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sighting getSightingById(int sightingId) {
        
         try {
            Sighting sighting = jdbcTemplate.queryForObject(" select * from sighting where sighting_id = ? ", new SightingMapper(), sightingId);
            sighting.setSuperPerson(findSuperPersonForSighting(sighting));
            sighting.setLocation(findLocationForSighting(sighting));
            return sighting;
        } catch (Exception ex) {
            return null;
        }
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Sighting> getSightingByDate(LocalDate Date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Sighting> getAllSighting() {
         List<Sighting> allSightings = jdbcTemplate.query("select * from sighting",
                new SightingMapper());
        return associateSightingAndLocationForSuperPerson(allSightings);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Sighting> getLatestSighting() {
          List<Sighting> latest = jdbcTemplate.query("select *  from sighting ORDER BY date desc limit 10 ",
                new SightingMapper());
        return associateSightingAndLocationForSuperPerson(latest);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
      protected static final class SightingMapper implements org.springframework.jdbc.core.RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {

            Sighting st = new Sighting();
            st.setSightingId(rs.getInt("sighting_id"));
            st.setDescription(rs.getString("description"));
            st.setDate(rs.getTimestamp("date").toLocalDateTime().toLocalDate());

            return st;
        }
    }
          private List<Sighting> associateSightingAndLocationForSuperPerson(List<Sighting> sightingList) {
        for (Sighting currentSighting : sightingList) {
            currentSighting.setSuperPerson(findSuperPersonForSighting(currentSighting));
            currentSighting.setLocation(findLocationForSighting(currentSighting));

        }
        return sightingList;

    }
              private Location findLocationForSighting(Sighting sighting) {
                  String s=
"select l.* from location l inner join sighting s on s.location_id = l.location_id where s.sighting_id = ?";
        return jdbcTemplate.queryForObject(s, new LocationMapper(), sighting.getSightingId());
    }

    private List<SuperHero> findSuperPersonForSighting(Sighting sighting) {
        
        String s="select s.*  from super_hero s  inner join  super_hero_sighting sps  on s.super_hero_id = sps.super_hero_id  inner join sighting  on sps.sighting_id = sighting.sighting_id  where sighting.sighting_id = ?";
        return jdbcTemplate.query(s,new SuperPersonMapper(), sighting.getSightingId());

    }
      protected static final class SuperPersonMapper implements org.springframework.jdbc.core.RowMapper<SuperHero> {

        @Override
        public SuperHero mapRow(ResultSet rs, int i) throws SQLException {
            SuperHero sp = new SuperHero();
            sp.setName(rs.getString("name"));
            sp.setPower(rs.getString("super_power"));
            sp.setDescription(rs.getString("description"));
            sp.setSuperHeroSide(rs.getBoolean("isHero"));
            sp.setSuperHeroId(rs.getInt("super_hero_id"));
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
