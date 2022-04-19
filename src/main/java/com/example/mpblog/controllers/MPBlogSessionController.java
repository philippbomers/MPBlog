package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.services.MPBlogSessionService;
import com.example.mpblog.services.MPBlogUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.Instant;
import java.util.Optional;

/**
 * Controller class for managing the sessions
 * Uses the session and user services
 */
@Controller
@ControllerAdvice
public class MPBlogSessionController {
    private final MPBlogSessionService mpBlogSessionService;
    private final MPBlogUserService mpBlogUserService;

    public MPBlogSessionController(MPBlogSessionService mpBlogSessionService,
                                   MPBlogUserService mpBlogUserService) {

        this.mpBlogSessionService = mpBlogSessionService;
        this.mpBlogUserService = mpBlogUserService;
    }

    /**
     * Getmapping method for the user login
     *
     * @param model used to transfer data from model to view through the controller
     * @return to the login template by view
     */
    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("login", new MPBlogUser());
        return "helpers/login";
    }

    /**
     * Postmapping method for the user login
     *
     * @param mpBlogUser is the user, whose credentials are given through the login Getmapping
     * @param bindingResult set of rules that the data should follow
     * @param response HttpServletResponse object to save the cookie(session) information
     * @param model used to transfer data from model to view through the controller
     * @return to the homepage if all the conditions are met and login is successful,
     *         to the login page otherwise.
     */
    @PostMapping("/login")
    public String loginResult(@Valid @ModelAttribute("login") MPBlogUser mpBlogUser,
                              BindingResult bindingResult,
                              HttpServletResponse response, Model model) {

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

    /**
     * Getmapping method to end an ongoing session
     *
     * @param sessionId id of the current session
     * @param response HttpServletResponse object to save the cookie(session) information
     * @return to the homepage after deleting the current session and clearing the cookie
     */
    @GetMapping("/logout")
    public String logout(@CookieValue(value = "sessionId", defaultValue = "") String sessionId,
                         HttpServletResponse response) {

        this.mpBlogSessionService.
                findById(sessionId).
                ifPresent(this.mpBlogSessionService::delete);

        Cookie cookie = new Cookie("sessionId", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }

    /**
     * Model attribute method to determine the current user through the session information
     *
     * @param sessionId id of the current session
     * @return the user of the current session, if the user exists, null otherwise
     */
    @ModelAttribute("sessionUser")
    public MPBlogUser sessionUser(@CookieValue(value = "sessionId", defaultValue = "") String sessionId) {

        if (sessionId != null &&
                !sessionId.isEmpty() &&
                !sessionId.isBlank()) {

            Optional<MPBlogSession> optionalSession = this.mpBlogSessionService.findById(sessionId);

            if (optionalSession.isPresent() &&
                    optionalSession.get().getExpiresAt().isAfter(Instant.now())) {

                MPBlogSession session = optionalSession.get();
                session.setExpiresAt();

                return session.getMpBlogUser();
            }
        }
        return null;
    }
}
