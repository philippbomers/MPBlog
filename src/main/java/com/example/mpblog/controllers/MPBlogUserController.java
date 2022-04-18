package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.repositories.MPBlogCommentRepository;
import com.example.mpblog.repositories.MPBlogEntryRepository;
import com.example.mpblog.services.MPBlogSessionService;
import com.example.mpblog.services.MPBlogUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MPBlogUserController {
    private final MPBlogUserService mpBlogUserService;
    private final MPBlogSessionService mpBlogSessionService;
    private final MPBlogEntryRepository mpBlogEntryRepository;
    private final MPBlogCommentRepository mpBlogCommentRepository;

    public MPBlogUserController(MPBlogUserService mpBlogUserService, MPBlogSessionService mpBlogSessionService, MPBlogEntryRepository mpBlogEntryRepository, MPBlogCommentRepository mpBlogCommentRepository) {
        this.mpBlogUserService = mpBlogUserService;
        this.mpBlogSessionService = mpBlogSessionService;
        this.mpBlogEntryRepository = mpBlogEntryRepository;
        this.mpBlogCommentRepository = mpBlogCommentRepository;
    }

    @GetMapping("/newuser")
    public String registerDialog(@CookieValue(name = "sessionId", required = false) String sessionId, Model model) {
        model.addAttribute("mpBlogUser", new MPBlogUser());
        return "new/newuser";
    }

    @PostMapping("/newuser")
    public String register(@Valid @ModelAttribute("mpBlogUser") MPBlogUser mpBlogUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new/newuser";
        } else {
            this.mpBlogUserService.addMPBlogUser(mpBlogUser);
            return "helpers/registersuccessfully";
        }
    }

    @GetMapping("/listuser")
    public String userList(Model model) {
        model.addAttribute("mpBlogUsers", this.mpBlogUserService.getMPBlogUsers());
        return "list/listuser";
    }

    @GetMapping("/switchAdminRights/{id}/listuser")
    public String register(@PathVariable int id, Model model) {
        this.mpBlogUserService.changeUserAdminStatus(this.mpBlogUserService.getMPBlogUser(id));
        model.addAttribute("mpBlogUsers", this.mpBlogUserService.getMPBlogUsers());
        return "redirect:/listuser";
    }

    @GetMapping("/showuser")
    public String userDetails(@CookieValue(name = "sessionId", required = false) String sessionId, Model model) {
        Optional<MPBlogUser> mpBlogUser = this.mpBlogSessionService.findMPBlogUserById(sessionId);
        model.addAttribute("numberOfEntries", mpBlogEntryRepository.countByMpBlogUser(mpBlogUser));
        model.addAttribute("numberOfComments", mpBlogCommentRepository.countByMpBlogUser(mpBlogUser));
        return sessionId == null || sessionId.isEmpty() ? "redirect:/" : "show/showuser";
    }

}
