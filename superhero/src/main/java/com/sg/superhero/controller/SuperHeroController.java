/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero.controller;

import com.sg.superhero.dao.OrganizationDao;
import com.sg.superhero.dao.SightingDao;
import com.sg.superhero.dao.SuperHeroDao;
import com.sg.superhero.model.Organization;
import com.sg.superhero.model.Sighting;
import com.sg.superhero.model.SuperHero;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author DivyaDeverapally
 */

@Controller
public class SuperHeroController {
    
    @Autowired
    SuperHeroDao superHeroDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    
   
  
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(HttpServletRequest request, Model model) {
        List<Sighting> sightingList = sightingDao.getLatestSighting();
        model.addAttribute("sightingList", sightingList);
        return "index";
    }
    
  @GetMapping(value = "superheros")
    public String displayAllSuperHeroes(HttpServletRequest request, Model model) {
      List<Organization> organizationList = organizationDao.getAllOrganization();
        List<SuperHero> heroList = superHeroDao.getAllSuperHero();
      
        model.addAttribute("heroList", heroList);
       model.addAttribute("organizationList", organizationList);
        return "superheros";

    }
    
    @PostMapping("addSuperHero")
    public String addSuperHero(HttpServletRequest rq, Model model){
        List<Organization> organizationList = new ArrayList<>();
        List<Integer> organizationsId = new ArrayList<>();
        //    String[] studentIds = request.getParameterValues("studentId");
        String[] orgId = rq.getParameterValues("organizationId");
        
       // int[] orgId = new int[organizationIdParameter.length];

        
        for (String organizationid : orgId) {
            Organization organization = organizationDao.getOrganizationById(Integer.parseInt(organizationid));
            organizationList.add(organization);
        }
        
        SuperHero sp = new SuperHero();
        sp.setName(rq.getParameter("name"));
        sp.setPower(rq.getParameter("power"));
        sp.setSuperHeroSide(Boolean.parseBoolean("true"));
        sp.setDescription(rq.getParameter("description"));
        sp.setOrganizations(organizationList);
superHeroDao.addSuperHero(sp);
        return "redirect:/superheros";
    }

    @GetMapping(value="editsuperhero")
    public String editSuperHero(Integer id, Model model){
       //  String superHeroIdParameter = rq.getParameter("superHeroId");
         int superHeroId= id;
          List<Organization> organizationList = organizationDao.getAllOrganization();
          SuperHero superPerson = superHeroDao.getSuperHeroById(superHeroId);
        model.addAttribute("hero", superPerson);
        model.addAttribute("organizationList", organizationList);
        return "editsuperhero";
    }
    
    @PostMapping("editsuperhero")
    public String saveEditSuperHero(SuperHero hero, HttpServletRequest rq){
          List<Organization> organizationList = new ArrayList<>();
        List<Integer> organizationsId = new ArrayList<>();
        //    String[] studentIds = request.getParameterValues("studentId");
        String[] orgId = rq.getParameterValues("organizationId");
        
       // int[] orgId = new int[organizationIdParameter.length];

        
        for (String organizationid : orgId) {
            Organization organization = organizationDao.getOrganizationById(Integer.parseInt(organizationid));
            organizationList.add(organization);
        }
        
        SuperHero sp = new SuperHero();
        sp.setName(rq.getParameter("name"));
        sp.setPower(rq.getParameter("power"));
        sp.setSuperHeroSide(Boolean.parseBoolean("true"));
        sp.setDescription(rq.getParameter("description"));
        sp.setOrganizations(organizationList);
superHeroDao.updateSuperHero(sp);
        return "redirect:/superheros";
    }
    
       @GetMapping("deleteHero")
    public String deleteHero(Integer id) {
        superHeroDao.deleteSuperHero(id);
        return "redirect:/superheros";
    }


}
