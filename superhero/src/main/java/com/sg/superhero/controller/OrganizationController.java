/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero.controller;

import com.sg.superhero.dao.LocationDao;
import com.sg.superhero.dao.OrganizationDao;
import com.sg.superhero.model.Location;
import com.sg.superhero.model.Organization;
import com.sg.superhero.model.Sighting;
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
public class OrganizationController {

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    LocationDao locationDao;

    @GetMapping(value = "organization")
    public String DisplayAllLocation(HttpServletRequest rq, Model model) {
        List<Organization> organizationList = orgDao.getAllOrganization();
        List<Location> locationList = locationDao.getAllLocation();
        model.addAttribute("organizationList", organizationList);
        model.addAttribute("locationList", locationList);

        return "organization";

    }

    @PostMapping("addOrganization")
    public String addOrganizationMethod(HttpServletRequest rq) {
        String name = rq.getParameter("name");
        String phone = rq.getParameter("phone");
        String desc = rq.getParameter("description");
        // String teacherId = request.getParameter("teacherId");
        Organization org = new Organization();
        org.setName(name);
        org.setPhone(phone);
        org.setDesciption(desc);

        Location location = locationDao.getLocationById(Integer.parseInt(rq.getParameter("locationId")));
        org.setLocation(location);

        orgDao.addOrganization(org);
        return "redirect:/organization";
    }

    @GetMapping("editOrgnization")
    public String editrganizationDisplay(Integer id, Model model) {
        Organization org = orgDao.getOrganizationById(id);

        List<Location> locationList = locationDao.getAllLocation();
        model.addAttribute("organization", org);
        model.addAttribute("locationList", locationList);

        return "editOrganization";
    }

    @PostMapping("editOrgnization")
    public String saveeditorganizationDisplay(Integer id, HttpServletRequest rq) {

        String name = rq.getParameter("name");
        String phone = rq.getParameter("phone");
        String desc = rq.getParameter("description");
        // String teacherId = request.getParameter("teacherId");
        Organization org = new Organization();
        org.setName(name);
        org.setPhone(phone);
        org.setDesciption(desc);
        org.setOrganizationId(Integer.parseInt(rq.getParameter("id")));

        Location location = locationDao.getLocationById(Integer.parseInt(rq.getParameter("locationId")));
        org.setLocation(location);
        orgDao.updateOrganization(org);
        return "redirect:/organization";
    }
    
    
           @GetMapping("deleteOrgnization")
     public  String deleteOrgnization(Integer id){
         orgDao.deleteOrganization(id);
           return "redirect:/organization";
         
     }
     
             @GetMapping("organizationDetails")
    public String organizationDetail(Integer id, Model model) {
        Organization org = orgDao.getOrganizationById(id);
        model.addAttribute("org", org);
        return "organizationDetails";
    }
}
