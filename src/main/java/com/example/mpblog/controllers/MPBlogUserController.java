package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.services.MPBlogUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MPBlogUserController {
    private final MPBlogUserService mpBlogUserService;

    public MPBlogUserController(MPBlogUserService mpBlogUserService) {
        this.mpBlogUserService = mpBlogUserService;
    }

    @GetMapping("/registerdialog")
    public String registerDialog(Model model) {
        model.addAttribute("mpBlogUser", new MPBlogUser());
        return "registerdialog";
    }

    @PostMapping("/registerdialog")
    public String register(@Valid @ModelAttribute("mpBlogUser") MPBlogUser mpBlogUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registerdialog";
        } else {
            this.mpBlogUserService.addMPBlogUser(mpBlogUser);
            return "registersuccessfully";
        }
    }

    @GetMapping("/bloguserlist")
    public String userList(Model model) {
        model.addAttribute("mpBlogUsers", this.mpBlogUserService.getMPBlogUsers());
        return "bloguserlist";
    }

    @GetMapping("/switchAdminRights/{id}/bloguserlist")
    public String register(@PathVariable int id, Model model) {
        this.mpBlogUserService.changeUserAdminStatus(this.mpBlogUserService.getMPBlogUsers(id));
        model.addAttribute("mpBlogUsers", this.mpBlogUserService.getMPBlogUsers());
        return "bloguserlist";
    }

}
