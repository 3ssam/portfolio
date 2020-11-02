package com.mandoob.controllers;

import com.mandoob.models.*;
import com.mandoob.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private TeamMemberService memberService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private GuestService guestService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String home(@RequestParam(required = false) String choose, Model model) {
        if (choose == null)
            return "Dashboard/" + "DashbordPage";
        if (choose.equalsIgnoreCase("teamMembers")) {
            List<TeamMember> members = memberService.getMembers();
            model.addAttribute("members", members);
            return "TeamMembers/" + "ManageTeamMembers";
        } else if (choose.equalsIgnoreCase("partner")) {
            List<Partner> partners = partnerService.getPartners();
            model.addAttribute("partners", partners);
            return "Partners/" + "ManagePartners";
        } else if (choose.equalsIgnoreCase("portfolio")) {
            List<Portfolio> portfolios = portfolioService.getPortfolios();
            model.addAttribute("portfolios", portfolios);
            return "Portfolios/" + "ManagePortfolios";
        } else if (choose.equalsIgnoreCase("guest")) {
            List<Guest> guests = guestService.getGuests();
            model.addAttribute("guests", guests);
            return "Guests/" + "ManageGuests";
        } else if (choose.equalsIgnoreCase("user")) {
            List<User> users = userService.getUsers();
            model.addAttribute("users", users);
            return "Users/" + "ManageUsers";
        }
        return "Dashboard/" + "DashbordPage";
    }


    @RequestMapping("login")
    public String login(Model model) {
        if (!userService.mailIsExist("admin@mandoob.com")) {
            User user = new User();
            user.setName("admin");
            user.setEmail("admin@mandoob.com");
            user.setPassword("123456789");
            userService.createUser(user);
        }
        return "Dashboard/" + "Login";
    }

    @RequestMapping("logout")
    public String logout(Model model) {
        return "Dashboard/" + "Login";
    }
}
