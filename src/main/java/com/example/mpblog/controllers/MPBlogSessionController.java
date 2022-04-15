package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.services.MPBlogSessionService;
import com.example.mpblog.services.MPBlogUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Optional;

@Controller
public class MPBlogSessionController {
    private final MPBlogSessionService mpBlogSessionService;
    private final MPBlogUserService mpBlogUserService;

    public MPBlogSessionController(MPBlogSessionService mpBlogSessionService, MPBlogUserService mpBlogUserService) {
        this.mpBlogSessionService = mpBlogSessionService;
        this.mpBlogUserService = mpBlogUserService;
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

    @GetMapping("/")
    public String home(@CookieValue(value="sessionId", defaultValue = "") String sessionId) {
        if(!sessionId.isEmpty()) {
            Optional<MPBlogSession> optionalSession = mpBlogSessionService.findByIdAndExpiresAtAfter(
                    sessionId, Instant.now());
            if (optionalSession.isPresent()) {
                MPBlogSession session = optionalSession.get();
// neues Ablaufdatum für die Session
                session.setExpiresAt(Instant.now().plusSeconds(7*24*60*60));
                MPBlogUser user = session.getMpBlogUser();
// User ist eingeloggt....
            }
        }
        return "home";
    }

}
