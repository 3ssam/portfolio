package com.mandoob.controllers;

import com.mandoob.Utils.PortfolioUtils;
import com.mandoob.models.Guest;
import com.mandoob.models.Partner;
import com.mandoob.models.Portfolio;
import com.mandoob.models.TeamMember;
import com.mandoob.services.GuestService;
import com.mandoob.services.PartnerService;
import com.mandoob.services.PortfolioService;
import com.mandoob.services.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/website")
public class WebsiteController {

    @Autowired
    private TeamMemberService memberService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private GuestService guestService;


    @PostMapping("/guest")
    public String addGuest(@Valid Guest guest, BindingResult result, Model model) {
        guestService.createGuest(guest);
        return "Website/" + "ContactUs";
    }

    @GetMapping("leadership")
    public String getTeamMembers(Model model) throws UnsupportedEncodingException {
        List<TeamMember> members = memberService.getMembers();
        for (int i = 0; i < members.size(); i++) {
            members.get(i).setImageSource(PortfolioUtils.convertToImage(members.get(i).getImageByte()));
        }
        model.addAttribute("members", members);
        return "Website/" + "TeamMembersPage";
    }

    @GetMapping("partners")
    public String getPartners(Model model) throws UnsupportedEncodingException {
        List<Partner> partners = partnerService.getPartners();
        for (int i = 0; i < partners.size(); i++) {
            partners.get(i).setImageSource(PortfolioUtils.convertToImage(partners.get(i).getImageByte()));
        }
        model.addAttribute("partners", partners);
        return "Website/" + "PartnersPage";
    }

    @GetMapping()
    public String getHome(Model model) throws UnsupportedEncodingException {
        return "Website/" + "HomePage";
    }

    @GetMapping("portfolio")
    public String getPortfolio(Model model) throws UnsupportedEncodingException {
        List<Portfolio> portfolios = portfolioService.getPortfolios();
        for (int i = 0; i < portfolios.size(); i++) {
            portfolios.get(i).setImageSource(PortfolioUtils.convertToImage(portfolios.get(i).getImageByte()));
        }
        model.addAttribute("portfolios", portfolios);
        return "Website/" + "PortfolioPage";
    }

    @GetMapping("contactus")
    public String contactUs(Model model) {
        Guest guest = new Guest();
        model.addAttribute("guest", guest);
        return "Website/" + "ContactUs";
    }

    @GetMapping("aboutus")
    public String aboutUs(Model model) {
        return "Website/" + "AboutUS";
    }


}
