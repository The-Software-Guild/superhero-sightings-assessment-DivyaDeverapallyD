/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero.controller;

import com.sg.superhero.dao.LocationDao;
import com.sg.superhero.model.Location;
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
public class LocationController {
    
    @Autowired
    LocationDao locationDao;
    
    @GetMapping(value="location")
     public String DisplayAllLocation(HttpServletRequest rq, Model model) {
        List<Location> locationList = locationDao.getAllLocation();
        model.addAttribute("locationList", locationList);
        return "location";

    }
     
     @PostMapping("addLocation")
     public String addLocation(HttpServletRequest rq){
         String lName= rq.getParameter("locationName");
         String lStreet= rq.getParameter("street");
         String lCity= rq.getParameter("city");
         String lState= rq.getParameter("state");
         String lZip= rq.getParameter("zip");
         String longitude= rq.getParameter("longitude");
         String latitude= rq.getParameter("latitude");
         String description=  rq.getParameter("description");
         
         Location location = new Location();
         location.setLocationName(lName);
         location.setState(lState);
         location.setCity(lCity);
         location.setStreet(lStreet);
         location.setZip(Integer.parseInt(lZip));
         location.setLongitude(Double.parseDouble(longitude));
         location.setLatitude(Double.parseDouble(latitude));
         location.setDescription(description);
         locationDao.addLocation(location);
         
         return "redirect:/location";
     }
     
     
     @GetMapping("deleteLocation")
     public  String deleteLocation(Integer id){
         locationDao.deleteLocation(id);
           return "redirect:/location";
         
     }
    // deleteOrgnization
     
  
     @GetMapping(value="editLocation")
      public String editLocation(Integer id, Model model){
          int lId=id;
          Location l= locationDao.getLocationById(id);
          model.addAttribute("location", l);
        
          return "editLocation";
      }
      
      
       @PostMapping("editLocation")
      public String updateLocation(HttpServletRequest rq,Location l){
          
          String lName= rq.getParameter("locationName");
         String lStreet= rq.getParameter("street");
         String lCity= rq.getParameter("city");
         String lState= rq.getParameter("state");
         String lZip= rq.getParameter("zip");
         String longitude= rq.getParameter("longitude");
         String latitude= rq.getParameter("latitude");
         String description=  rq.getParameter("description");
         
         Location location = new Location();
         location.setLocationName(lName);
         location.setState(lState);
         location.setCity(lCity);
         location.setStreet(lStreet);
         location.setZip(Integer.parseInt(lZip));
         location.setLongitude(Double.parseDouble(longitude));
         location.setLatitude(Double.parseDouble(latitude));
         location.setDescription(description);
         locationDao.updateLocation(location);
        
          return "redirect:/location";
      }
    
}
