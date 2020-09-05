package ru.denis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.denis.model.User;
import ru.denis.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/admin")
    public String toAdminPage(Model model) {
        List<User> userList = userService.allUsers();
        model.addAttribute("userList", userList);
        return "admin/adminList";
    }

    @GetMapping(value = "admin/addUser")
    public String newUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "admin/createUser";
    }

    @RequestMapping(value = "admin/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user) {
        setRole(user);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "admin/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/editUser");
        Optional<User> user = userService.findById(id);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "admin/edit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("user") User user) {
        setRole(user);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    private void setRole(User user) {
        user.setRoles(new HashSet<>());
        user.getRoles().add(userService.findRoleByName("ROLE_USER"));
    }
}
