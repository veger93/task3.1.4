package ru.kata.spring.boot_security.demo.controller;


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
        model.addAttribute("thisUser", userService.loadUserByUsername(principal.getName()));
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("newUser", new User());
        return "/allUsersAdm";
    }
//    @GetMapping("/addNewUser")
//    public String addNewUser(Model model) {
//        User user = new User();
//        model.addAttribute("user", user);
//        model.addAttribute("roles", roleService.findAll());
//        return "/userInfAdm";
//    }
    @PostMapping("/admin")
    public String saveUser(@ModelAttribute("newUser") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
//    @GetMapping("/updateUser")
//    public String updateUser(@RequestParam("userId") Long id, Model model) {
//        User user = userService.getUserById(id);
//        model.addAttribute("user", user);
//        model.addAttribute("roles", roleService.findAll());
//        return "/userInfAdm";
//    }

    @PostMapping("admin/updateUser")
    public String edit(@ModelAttribute("userToEdit") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("admin/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
