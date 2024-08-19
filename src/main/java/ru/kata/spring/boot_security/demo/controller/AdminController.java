package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping()
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Principal principal, Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("allUsers", users);
        User currentUser = userService.loadUserByUsername(principal.getName());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("newUser", new User());
        return "/allUsersAdm";
    }

    @PostMapping("/admin")
    public String saveUser(@ModelAttribute("newUser") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, User user) {
        user.setId(id);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("admin/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
