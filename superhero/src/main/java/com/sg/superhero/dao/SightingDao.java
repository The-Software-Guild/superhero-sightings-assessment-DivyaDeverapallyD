/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero.dao;

import com.sg.superhero.model.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author DivyaDeverapally
 */
public interface SightingDao {
     public void addSighting(Sighting sighting);

    public void deletSigting(int sightingId);
    
    public void updateSighting(Sighting sighting);

    public Sighting getSightingById(int sightingId);

    public List<Sighting> getSightingByDate(LocalDate Date);

    public List<Sighting> getAllSighting();
    
    public List<Sighting> getLatestSighting();
}
