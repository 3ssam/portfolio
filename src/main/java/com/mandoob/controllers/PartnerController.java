package com.mandoob.controllers;

import com.mandoob.Utils.PortfolioUtils;
import com.mandoob.exceptions.FileStorageException;
import com.mandoob.models.Partner;
import com.mandoob.services.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("partner")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;


    @PostMapping()
    public String addPartner(@Valid Partner partner, @RequestParam("image") MultipartFile multipartFile, BindingResult result, Model model) {
        if (partner.getId() != 0 && partnerService.getPartners().size() >= 1)
            model.addAttribute("isUpdated", true);
        else
            model.addAttribute("isUpdated", false);
        if (result.hasErrors())
            return "Partners/" + "AddPartner";
        if (!multipartFile.getOriginalFilename().isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            try {
                // Check if the file's name contains invalid characters
                if (fileName.contains("..")) {
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                }
                partner.setImageName(fileName);
                partner.setImagetype(multipartFile.getContentType());

                partner.setImageByte(PortfolioUtils.compressBytes(multipartFile.getBytes()));
                partnerService.createPartner(partner);
            } catch (IOException ex) {
                throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
            }
        } else {
            Partner orignalPartner = partnerService.getPartner(partner.getId());
            orignalPartner.setName(partner.getName());
            orignalPartner.setBio(partner.getBio());
            partnerService.createPartner(orignalPartner);
        }
        model.addAttribute("isAdded", !(boolean) model.getAttribute("isUpdated"));
        model.addAttribute("isDeleted", false);
        List<Partner> partners = partnerService.getPartners();
        model.addAttribute("partners", partners);
        return "Partners/" + "ManagePartners";
    }


    @GetMapping()
    public String addPartner(Model model) {
        Partner partner = new Partner();
        model.addAttribute("partner", partner);
        return "Partners/" + "AddPartner";
    }

    @GetMapping("delete/{id}")
    public String deletePartner(@PathVariable("id") long id, Model model) {
        partnerService.deletePartner(id);
        List<Partner> partners = partnerService.getPartners();
        model.addAttribute("partners", partners);
        model.addAttribute("isDeleted", true);
        return "Partners/" + "ManagePartners";
    }

    @GetMapping("image/{id}")
    public String showPartnerImage(@PathVariable("id") long id, Model model) throws UnsupportedEncodingException {
        Partner partner = partnerService.getPartner(id);
        String image = PortfolioUtils.convertToImage(partner.getImageByte());
        model.addAttribute("image", image);
        return "Dashboard/" + "ShowImage";
    }


    @GetMapping("edit/{id}")
    public String editPartner(@PathVariable("id") long id, Model model) {
        Partner partner = partnerService.getPartner(id);
        model.addAttribute("partner", partner);
        return "Partners/" + "EditPartner";
    }
}
