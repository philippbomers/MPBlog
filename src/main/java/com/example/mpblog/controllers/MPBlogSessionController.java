package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.repositories.MPBlogSessionRepository;
import com.example.mpblog.services.MPBlogSessionService;
import com.example.mpblog.services.MPBlogUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@ControllerAdvice
public class MPBlogSessionController {
    private final MPBlogSessionService mpBlogSessionService;
    private final MPBlogUserService mpBlogUserService;

    private final MPBlogSessionRepository mpBlogSessionRepository;

    public MPBlogSessionController(MPBlogSessionService mpBlogSessionService, MPBlogUserService mpBlogUserService, MPBlogSessionRepository mpBlogSessionRepository) {
        this.mpBlogSessionService = mpBlogSessionService;
        this.mpBlogUserService = mpBlogUserService;
        this.mpBlogSessionRepository = mpBlogSessionRepository;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new MPBlogUser());
        return "helpers/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("login") MPBlogUser mpBlogUser, BindingResult bindingResult, HttpServletResponse response, Model model) {
        Optional<MPBlogUser> optionalUser = this.mpBlogUserService.getMPBlogUsers(mpBlogUser);
        boolean isInvalid = false;
        if (!bindingResult.hasErrors() && optionalUser.isEmpty()) {
            isInvalid = true;
        } else if (optionalUser.isPresent() && !bindingResult.hasErrors()) {
            MPBlogSession mpBlogSession = new MPBlogSession();
            mpBlogSession.setMpBlogUser(optionalUser.get());
            mpBlogSession.setExpiresAt();
            this.mpBlogSessionService.save(mpBlogSession);
            Cookie cookie = new Cookie("sessionId", mpBlogSession.getId());
            response.addCookie(cookie);
            return "redirect:/";
        }
        model.addAttribute("isInvalid", isInvalid);
        return "helpers/login";
    }

    @PostMapping("/logout")
    public String logout(@CookieValue(value = "sessionId", defaultValue = "") String sessionId, HttpServletResponse response) {
        Optional<MPBlogSession> optionalSession = this.mpBlogSessionService.findById(sessionId);
        optionalSession.ifPresent(this.mpBlogSessionRepository::delete);
        Cookie cookie = new Cookie("sessionId", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }


    @ModelAttribute("sessionUser")
    public MPBlogUser sessionUser(@CookieValue(value = "sessionId", defaultValue = "") String sessionId) {
        if (sessionId != null && !sessionId.isEmpty() && !sessionId.isBlank()) {
            Optional<MPBlogSession> optionalSession = this.mpBlogSessionService.findById(sessionId);
            if (optionalSession.isPresent()) {
                MPBlogSession session = optionalSession.get();
                session.setExpiresAt();
                return session.getMpBlogUser();
            }
        }
        return null;
    }

}
