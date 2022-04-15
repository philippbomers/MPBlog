package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.services.MPBlogSessionService;
import com.example.mpblog.services.MPBlogUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.Instant;
import java.util.Optional;

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

    @PostMapping("/login")
    public String login(HttpServletResponse response) {
        Optional<MPBlogUser> optionalUser = mpBlogUserService.findByUsernameAndPassword("...", "...");
        if (optionalUser.isPresent()) {
            MPBlogSession mpBlogSession = new MPBlogSession(optionalUser.get(), Instant.now().plusSeconds(7*24*60*60));
            mpBlogSessionService.save(mpBlogSession);
            Cookie cookie = new Cookie("sessionId", mpBlogSession.getId());
            response.addCookie(cookie);
// Login erfolgreich
            return "redirect:/";
        }
// Login nicht erfolgreich
        return "login";
    }
}
