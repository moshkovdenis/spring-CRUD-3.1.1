package ru.denis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.denis.model.User;
import ru.denis.service.UserService;

import java.util.HashSet;


@Controller
public class MainPageController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String registration(@ModelAttribute("user") User user) {
        user.setRoles(new HashSet<>());
        if (user.getName().equals("admin")) {
            user.getRoles().add(userService.findRoleByName("ROLE_ADMIN"));
        } else {
            user.getRoles().add(userService.findRoleByName("ROLE_USER"));
        }
        userService.addUser(user);
        return "redirect:/login";
    }

    @RequestMapping(value = "/registration")
    public String newUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }
}
