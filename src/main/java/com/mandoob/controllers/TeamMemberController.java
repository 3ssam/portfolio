package com.mandoob.controllers;

import com.mandoob.Utils.PortfolioUtils;
import com.mandoob.exceptions.FileStorageException;
import com.mandoob.models.TeamMember;
import com.mandoob.services.TeamMemberService;
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
@RequestMapping("teamMember")
public class TeamMemberController {

    @Autowired
    private TeamMemberService memberService;


    @PostMapping()
    public String addTeamMember(@Valid TeamMember member, @RequestParam("image") MultipartFile multipartFile, BindingResult result, Model model) {
        if (member.getId() != 0 && memberService.getMembers().size() >= 1)
            model.addAttribute("isUpdated", true);
        else
            model.addAttribute("isUpdated", false);
        if (result.hasErrors())
            return "TeamMembers/" + "AddTeamMember";
        if (!multipartFile.getOriginalFilename().isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            try {
                // Check if the file's name contains invalid characters
                if (fileName.contains("..")) {
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                }
                member.setImageName(fileName);
                member.setImagetype(multipartFile.getContentType());
                member.setImageByte(PortfolioUtils.compressBytes(multipartFile.getBytes()));
                memberService.createMember(member);
            } catch (IOException ex) {
                throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
            }
        } else {
            TeamMember orignalMember = memberService.getMember(member.getId());
            orignalMember.setName(member.getName());
            orignalMember.setBio(member.getBio());
            memberService.createMember(orignalMember);
        }
        model.addAttribute("isAdded", !(boolean) model.getAttribute("isUpdated"));
        model.addAttribute("isDeleted", false);
        List<TeamMember> members = memberService.getMembers();
        model.addAttribute("members", members);
        return "TeamMembers/" + "ManageTeamMembers";
    }


    @GetMapping()
    public String addTeamMember(Model model) {
        TeamMember member = new TeamMember();
        model.addAttribute("member", member);
        return "TeamMembers/" + "AddTeamMember";
    }

    @GetMapping("delete/{id}")
    public String deleteTeamMember(@PathVariable("id") long id, Model model) {
        memberService.deleteMember(id);
        List<TeamMember> members = memberService.getMembers();
        model.addAttribute("members", members);
        model.addAttribute("isDeleted", true);
        return "TeamMembers/" + "ManageTeamMembers";
    }

    @GetMapping("image/{id}")
    public String showTeamMemberImage(@PathVariable("id") long id, Model model) throws UnsupportedEncodingException {
        TeamMember member = memberService.getMember(id);
        String image = PortfolioUtils.convertToImage(member.getImageByte());
        model.addAttribute("image", image);
        return "Dashboard/" + "ShowImage";
    }


    @GetMapping("edit/{id}")
    public String editTeamMember(@PathVariable("id") long id, Model model) {
        TeamMember member = memberService.getMember(id);
        model.addAttribute("member", member);
        return "TeamMembers/" + "EditTeamMember";
    }
}
