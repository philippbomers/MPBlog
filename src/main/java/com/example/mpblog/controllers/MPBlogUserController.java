package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.services.MPBlogUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class MPBlogUserController {
    private final MPBlogUserService mpBlogUserService;

    public MPBlogUserController(MPBlogUserService mpBlogUserService) {
        this.mpBlogUserService = mpBlogUserService;
    }

    @GetMapping("/newuser")
    public String registerDialog(@CookieValue(name = "sessionId", required = false) String sessionId, Model model) {
        model.addAttribute("mpBlogUser", new MPBlogUser());
        return "newUser";
    }

    @PostMapping("/newuser")
    public String register(@Valid @ModelAttribute("mpBlogUser") MPBlogUser mpBlogUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "newUser";
        } else {
            this.mpBlogUserService.addMPBlogUser(mpBlogUser);
            return "registerSuccessfully";
        }
    }

    @GetMapping("/listuser")
    public String userList(Model model) {
        model.addAttribute("mpBlogUsers", this.mpBlogUserService.getMPBlogUsers());
        return "listUser";
    }

    @GetMapping("/switchAdminRights/{id}/listuser")
    public String register(@PathVariable int id, Model model) {
        this.mpBlogUserService.changeUserAdminStatus(this.mpBlogUserService.getMPBlogUser(id));
        model.addAttribute("mpBlogUsers", this.mpBlogUserService.getMPBlogUsers());
        return "redirect:/listuser";
    }

    @GetMapping("/showuser")
    public String userDetails(@CookieValue(name = "sessionId", required = false) String sessionId) {
        return sessionId == null || sessionId.isEmpty() ? "redirect:/" : "showUser";
    }
}
