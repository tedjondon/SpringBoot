//package com.zabello.springboot.controller;
//
//import com.zabello.springboot.model.Role;
//import com.zabello.springboot.model.User;
//import com.zabello.springboot.service.RoleService;
//import com.zabello.springboot.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminsController {
//
//    private UserService userService;
//    private RoleService roleService;
//
//    @Autowired
//    public AdminsController(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//    @GetMapping
//    public String index(Model model) {
//        List<User> users = userService.listUsers();
//        List<Role> roles = roleService.listRoles();
//        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        model.addAttribute("userList", users);
//        model.addAttribute("roleList", roles);
//        model.addAttribute("currentUser", currentUser);
//        return "users";
//    }
//
//    @PostMapping
//    public String create(@ModelAttribute("user") User user,
//                         @RequestParam("rolesArr") String[] role) {
//        user.setRoles(roleService.arrayToSet(role));
//        userService.add(user);
//        return "redirect:/admin";
//    }
//
//    @PostMapping("/edit")
//    public String editUser(@ModelAttribute User user,
//                           @RequestParam(name = "newPassword", required = false) String newPassword,
//                           @RequestParam(name = "editedUsersRoles", required = false) String[] roles) {
//        if (!newPassword.isEmpty()) {
//            user.setPassword(newPassword);
//        }
//        if (roles != null) {
//            user.setRoles(roleService.arrayToSet(roles));
//        } else {
//            user.setRoles(userService.getUserById(user.getId()).getRoles());
//        }
//        userService.updateUser(user.getId(), user);
//        return "redirect:/admin";
//    }
//
//    @PostMapping("/delete")
//    public String deleteUser(@ModelAttribute User user) {
//        userService.deleteUser(user.getId());
//        return "redirect:/admin";
//    }
//}