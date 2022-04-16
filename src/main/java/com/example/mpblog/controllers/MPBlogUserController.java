package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.services.MPBlogSessionService;
import com.example.mpblog.services.MPBlogUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class MPBlogUserController {
    private final MPBlogUserService mpBlogUserService;
    private final MPBlogSessionService mpBlogSessionService;

    public MPBlogUserController(MPBlogUserService mpBlogUserService, MPBlogSessionService mpBlogSessionService) {
        this.mpBlogUserService = mpBlogUserService;
        this.mpBlogSessionService = mpBlogSessionService;
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
        this.mpBlogUserService.changeUserAdminStatus(this.mpBlogUserService.getMPBlogUser(id));
        model.addAttribute("mpBlogUsers", this.mpBlogUserService.getMPBlogUsers());
        return "redirect:/bloguserlist";
    }

    @GetMapping("/userdetails")
    public String userDetails(@CookieValue(name = "sessionId") String sessionId, Model model){
        model.addAttribute("sessionUser", mpBlogSessionService.findById(sessionId).get().getMpBlogUser());
        model.addAttribute("sessionId", sessionId);
        return "userdetails";
    }

}
