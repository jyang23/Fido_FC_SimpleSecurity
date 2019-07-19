package com.jy.fido_fridaychallenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private PetRepository petRepository;

    @RequestMapping("/")
    public String index(Model model)
    {
        model.addAttribute("pets", petRepository.findAll());
        return "index";
    }
    //----------------------------------------------------------------------------------------------------------------------
    @RequestMapping("/loadfilter")
    public String loadFormPage() {
        return "filter";
    }

    @PostMapping("/processfilter")
    public String processForm(@RequestParam(value = "ownerfirstname",required=false) String ownerfirstname,
                              @RequestParam(value = "ownerlastname",required=false) String ownerlastname,
                              @RequestParam(value = "petname",required=false) String petname,
                              @RequestParam(value = "datelost",required=false) String datelost,
                              @RequestParam(value = "breed",required=false) String breed,
                              @RequestParam(value = "status",required=false) String status, Model model){
        if(ownerfirstname != null)
        {
            model.addAttribute("pets", petRepository.findByOwnerfirstname(ownerfirstname));
        }
        else if(ownerlastname != null)
        {
            model.addAttribute("pets", petRepository.findByOwnerlastname(ownerlastname));
        }
        else if(petname != null)
        {
            model.addAttribute("pets", petRepository.findByPetname(petname));
        }
        else if(datelost != null)
        {
            model.addAttribute("pets", petRepository.findByDatelost(datelost));
        }
        else if(breed != null)
        {
            model.addAttribute("pets", petRepository.findByBreed(breed));
        }
        else if(status != null)
        {
            model.addAttribute("pets", petRepository.findByStatus(status));
        }
        return "confirm";
    }
    //----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/add")
    public String petForm(Model model)
        {
            model.addAttribute("pet",new Pet());
            return "petform";
        }
    @PostMapping("/processform")
    public String processForm(@Valid Pet pet, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "petform";
        }
        petRepository.save(pet);
        return "redirect:/";
    }
    //----------------------------------------------------------------------------------------------------------------------
    @RequestMapping("/update/{id}")
    public String updatePetForm(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("pet", petRepository.findById(id).get());
        return "petform";
    }
    //----------------------------------------------------------------------------------------------------------------------
    @RequestMapping("/logout")
    public String logout()
    {
        return "redirect:/logout";
    }


}