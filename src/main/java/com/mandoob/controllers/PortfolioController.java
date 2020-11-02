package com.mandoob.controllers;

import com.mandoob.Utils.PortfolioUtils;
import com.mandoob.exceptions.FileStorageException;
import com.mandoob.models.Portfolio;
import com.mandoob.services.PortfolioService;
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
@RequestMapping("portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;


    @PostMapping()
    public String addPortfolio(@Valid Portfolio portfolio, @RequestParam("image") MultipartFile multipartFile, BindingResult result, Model model) {
        if (portfolio.getId() != 0 && portfolioService.getPortfolios().size() >= 1)
            model.addAttribute("isUpdated", true);
        else
            model.addAttribute("isUpdated", false);
        if (result.hasErrors())
            return "Portfolios/" + "AddPortfolio";
        if (!multipartFile.getOriginalFilename().isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            try {
                // Check if the file's name contains invalid characters
                if (fileName.contains("..")) {
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                }
                portfolio.setImageName(fileName);
                portfolio.setImagetype(multipartFile.getContentType());
                portfolio.setImageByte(PortfolioUtils.compressBytes(multipartFile.getBytes()));
                portfolioService.createPortfolio(portfolio);
            } catch (IOException ex) {
                throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
            }
        } else {
            Portfolio orignalPortfolio = portfolioService.getPortfolio(portfolio.getId());
            orignalPortfolio.setName(portfolio.getName());
            orignalPortfolio.setBio(portfolio.getBio());
            portfolioService.createPortfolio(orignalPortfolio);
        }
        model.addAttribute("isAdded", !(boolean) model.getAttribute("isUpdated"));
        model.addAttribute("isDeleted", false);
        List<Portfolio> portfolios = portfolioService.getPortfolios();
        model.addAttribute("portfolios", portfolios);
        return "Portfolios/" + "ManagePortfolios";
    }


    @GetMapping()
    public String addPortfolio(Model model) {
        Portfolio portfolio = new Portfolio();
        model.addAttribute("portfolio", portfolio);
        return "Portfolios/" + "AddPortfolio";
    }

    @GetMapping("delete/{id}")
    public String deletePortfolio(@PathVariable("id") long id, Model model) {
        portfolioService.deletePortfolio(id);
        List<Portfolio> portfolios = portfolioService.getPortfolios();
        model.addAttribute("portfolios", portfolios);
        model.addAttribute("isDeleted", true);
        return "Portfolios/" + "ManagePortfolios";
    }

    @GetMapping("image/{id}")
    public String showPortfolioImage(@PathVariable("id") long id, Model model) throws UnsupportedEncodingException {
        Portfolio portfolio = portfolioService.getPortfolio(id);
        String image = PortfolioUtils.convertToImage(portfolio.getImageByte());
        model.addAttribute("image", image);
        return "Dashboard/" + "ShowImage";
    }


    @GetMapping("edit/{id}")
    public String editPortfolio(@PathVariable("id") long id, Model model) {
        Portfolio portfolio = portfolioService.getPortfolio(id);
        model.addAttribute("portfolio", portfolio);
        return "Portfolios/" + "EditPortfolio";
    }
}
