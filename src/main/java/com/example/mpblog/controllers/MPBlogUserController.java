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

    @GetMapping("/newUser")
    public String newUser(Model model) {

        model.addAttribute("mpBlogUser", new MPBlogUser());
        return "new/newUser";
    }

    @PostMapping("/newUser")
    public String newUserResult(@Valid @ModelAttribute("mpBlogUser") MPBlogUser mpBlogUser,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "new/newUser";
        }

        this.mpBlogUserService.addMPBlogUser(mpBlogUser);
        return "registerSuccessfully";
    }

    @GetMapping("/listUser")
    public String listUser(Model model) {

        model.addAttribute("mpBlogUsers", this.mpBlogUserService.getMPBlogUsers());
        return "list/listUser";
    }

    @GetMapping("/switchAdminRights/{id}/listUser")
    public String switchAdminRights(@PathVariable int id,
                                    Model model) {

        this.mpBlogUserService.
                changeUserAdminStatus(this.mpBlogUserService.
                        getMPBlogUser(id));

        model.addAttribute("mpBlogUsers", this.mpBlogUserService.getMPBlogUsers());
        return "redirect:/listUser";
    }

    @GetMapping("/showUser")
    public String showUser(@CookieValue(name = "sessionId", required = false) String sessionId,
                           Model model) {

        this.session
        this.mpBlogUserService.getMPBlogUser(id);

        model.addAttribute("numberOfEntries", this.)

        return sessionId == null ||
                sessionId.isEmpty() ?
                "redirect:/" :
                "show/showUser";
    }
}
