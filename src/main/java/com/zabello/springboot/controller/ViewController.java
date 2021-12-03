package com.zabello.springboot.controller;


import com.zabello.springboot.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class ViewController {

    @GetMapping("/admin/users")
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelAndView.addObject("loggedInUser", loggedInUser);
        return modelAndView;
    }
}