package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.services.MPBlogSessionService;
import com.example.mpblog.services.MPBlogUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MPBlogSessionController {
    private final MPBlogSessionService mpBlogSessionService;
    private final MPBlogUserService mpBlogUserService;

    public MPBlogSessionController(MPBlogSessionService mpBlogSessionService, MPBlogUserService mpBlogUserService) {
        this.mpBlogSessionService = mpBlogSessionService;
        this.mpBlogUserService = mpBlogUserService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new MPBlogUser());
        return "login";
    }

    @PostMapping("/login")
    public String login(@CookieValue(value = "sessionId", defaultValue = "") String sessionId, @Valid @ModelAttribute("login") MPBlogUser mpBlogUser, BindingResult bindingResult, HttpServletResponse response) {
        Optional<MPBlogUser> optionalUser = this.mpBlogUserService.getMPBlogUsers(mpBlogUser);
        if (sessionId.isEmpty() && optionalUser.isPresent() && !bindingResult.hasErrors()) {
            MPBlogSession mpBlogSession = new MPBlogSession();
            mpBlogSession.setMpBlogUser(optionalUser.get());
            mpBlogSession.setExpiresAt();
            this.mpBlogSessionService.save(mpBlogSession);
            Cookie cookie = new Cookie("sessionId", mpBlogSession.getId());
            response.addCookie(cookie);
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/")
    public String home(@CookieValue(value = "sessionId", defaultValue = "") String sessionId) {
        if (!sessionId.isEmpty()) {
            Optional<MPBlogSession> optionalSession = mpBlogSessionService.findByIdAndExpiresAtAfter(
                    sessionId);
            if (optionalSession.isPresent()) {
                MPBlogSession session = optionalSession.get();
                session.setExpiresAt();
            }
        }
        return "homepage";
    }

}
