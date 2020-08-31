package ru.denis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.denis.model.User;
import ru.denis.service.UserService;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public String homePage(ModelMap model) {
        List<User> userList = userService.allUsers();
        model.addAttribute("userList", userList);
        return "index";
    }

    @RequestMapping(value = "/newUser")
    public String newUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "createUser";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editPage(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("editUser");
        User user = userService.findById(id);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
