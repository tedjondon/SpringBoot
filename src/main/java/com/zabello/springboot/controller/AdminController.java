package com.zabello.springboot.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zabello.springboot.model.Role;
import com.zabello.springboot.model.User;
import com.zabello.springboot.service.RoleService;
import com.zabello.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(@Qualifier("userServiceImpl") UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> allUsers() {
        return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);
    }
    @GetMapping("/allRoles")
    public ResponseEntity<List<Role>> allRoles() {
        return new ResponseEntity<>(roleService.listRoles(), HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<Void> editUser(@RequestBody User user) {
        if (user.getRoles().isEmpty()) {
            user.setRoles(userService.getUserById(user.getId()).getRoles());
        }
        user.setPassword(user.getPassword());
        userService.updateUser(user.getId(), user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveUser(@RequestBody String user) {
        ObjectMapper mapper = new ObjectMapper();
        User editedUser = new User();
        try {
            editedUser = mapper.readValue(user, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!userService.add(editedUser)) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
