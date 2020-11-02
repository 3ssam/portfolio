package com.mandoob.controllers;

import com.mandoob.models.User;
import com.mandoob.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping()
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (user.getId() != 0 && userService.getUsers().size() >= 1)
            model.addAttribute("isUpdated", true);
        else
            model.addAttribute("isUpdated", false);
        if (result.hasErrors())
            return "Users/" + "AddUser";
        userService.createUser(user);
        model.addAttribute("isAdded", !(boolean) model.getAttribute("isUpdated"));
        model.addAttribute("isDeleted", false);
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "Users/" + "ManageUsers";
    }


    @GetMapping()
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "Users/" + "AddUser";
    }


    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userService.deleteUser(id);
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        model.addAttribute("isDeleted", true);
        return "Users/" + "ManageUsers";
    }

    @GetMapping("{id}")
    public String editUser(@PathVariable("id") long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "Users/" + "EditUser";
    }
}
