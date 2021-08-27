/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero.controller;

import com.sg.superhero.dao.LocationDao;
import com.sg.superhero.dao.SightingDao;
import com.sg.superhero.dao.SuperHeroDao;
import com.sg.superhero.model.Location;
import com.sg.superhero.model.Sighting;
import com.sg.superhero.model.SuperHero;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author DivyaDeverapally
 */
@Controller
public class SightingController {
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperHeroDao heroDao;
    
    @Autowired
    LocationDao locationDao;
    
    
    @GetMapping("sighting")
     public String DisplayAllSighting(HttpServletRequest rq, Model model) {
        List<Sighting> sightingList = sightingDao.getAllSighting();
        List<SuperHero> heroList = heroDao.getAllSuperHero();
        List<Location> locationList = locationDao.getAllLocation();
        model.addAttribute("sightingList", sightingList);
          model.addAttribute("heroList", heroList);
            model.addAttribute("locationList", locationList);
        
        
        return "sighting";

    }
     
     @PostMapping("addSighting")
     public String saveSighting(Sighting sighting, HttpServletRequest request){
         String locatioId= request.getParameter("locationId");
           String[] superHeroIds = request.getParameterValues("superHeroId");
           
           sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locatioId)));
           
           
           List<SuperHero> heroes = new ArrayList<>();
        for(String heroId : superHeroIds) {
            heroes.add(heroDao.getSuperHeroById(Integer.parseInt(heroId)));
        }
           // '2021-08-28'
           
        sighting.setSuperPerson(heroes);
        sighting.setDate(LocalDate.parse(request.getParameter("date")));
           sightingDao.addSighting(sighting);
        
         return "redirect:/sighting";
     }
     
     
     @GetMapping("editSighting")
     public String displayEditSighting(Integer id, Model model){
         Sighting s= sightingDao.getSightingById(id);
         List<SuperHero> heroList = heroDao.getAllSuperHero();
        List<Location> locationList = locationDao.getAllLocation();
        model.addAttribute("sighting", s);
          model.addAttribute("heroList", heroList);
            model.addAttribute("locationList", locationList);
        
         return "editSighting";
     }
     
     @PostMapping("editSighting")
     public String updateSighting(Sighting sighting, HttpServletRequest request){
            String locatioId= request.getParameter("locationId");
           String[] superHeroIds = request.getParameterValues("superHeroId");
           
           sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locatioId)));
           
           
           List<SuperHero> heroes = new ArrayList<>();
        for(String heroId : superHeroIds) {
            heroes.add(heroDao.getSuperHeroById(Integer.parseInt(heroId)));
        }
           // '2021-08-28'
           
        sighting.setSuperPerson(heroes);
        sighting.setDate(LocalDate.parse(request.getParameter("date")));
        sightingDao.updateSighting(sighting);
        return "redirect:/sighting"; 
     }
     
     @GetMapping("deleteSighting")
     public String deleteSighting(int id){
         sightingDao.deletSigting(id);
           return "redirect:/sighting"; 
         
     }
    
}

