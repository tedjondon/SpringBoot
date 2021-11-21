package com.zabello.springboot.controller;

import com.zabello.springboot.model.User;
import com.zabello.springboot.service.RoleService;
import com.zabello.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminsController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminsController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String index(Model model) {
        List<User> users = userService.listUsers();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute(userService.getUserById(id));
        return "show";
    }

    @GetMapping("new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("rolesList", roleService.listRoles());
        return "new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("rolesArr") String[] role) {
        user.setRoles(roleService.arrayToSet(role));
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("rolesList", roleService.listRoles());
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") int id,
                         @RequestParam("rolesArr") String[] role) {
        user.setRoles(roleService.arrayToSet(role));
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}