package com.mandoob.controllers;

import com.mandoob.models.Guest;
import com.mandoob.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("guest")
public class GuestController {

    @Autowired
    private GuestService guestService;


    @GetMapping()
    public String addGuest(Model model) {
        Guest guest = new Guest();
        model.addAttribute("guest", guest);
        return "Guests/" + "AddGuest";
    }

    @GetMapping("delete/{id}")
    public String deleteGuest(@PathVariable("id") long id, Model model) {
        guestService.deleteGuest(id);
        List<Guest> guests = guestService.getGuests();
        model.addAttribute("guests", guests);
        model.addAttribute("isDeleted", true);
        return "Guests/" + "ManageGuests";
    }

    @GetMapping("{id}")
    public String editGuest(@PathVariable("id") long id, Model model) {
        Guest guest = guestService.getGuest(id);
        model.addAttribute("guest", guest);
        return "Guests/" + "ViewGuest";
    }
}
