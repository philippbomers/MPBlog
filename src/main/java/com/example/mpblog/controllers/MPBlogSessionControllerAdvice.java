package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.repositories.MPBlogSessionRepository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.Instant;
import java.util.Optional;

@ControllerAdvice
public class MPBlogSessionControllerAdvice {
    private final MPBlogSessionRepository mpBlogSessionRepository;

    public MPBlogSessionControllerAdvice(MPBlogSessionRepository mpBlogSessionRepository) {
        this.mpBlogSessionRepository = mpBlogSessionRepository;
    }

    @ModelAttribute("sessionUser")
    public MPBlogUser sessionUser(@CookieValue(value = "sessionId", defaultValue = "") String sessionId) {
        if (!sessionId.isEmpty()) {
            Optional<MPBlogSession> optionalSession = mpBlogSessionRepository.findByIdAndExpiresAtAfter(sessionId, Instant.now().plusSeconds(7 * 24 * 60 * 60));
            if (optionalSession.isPresent()) {
                MPBlogSession session = optionalSession.get();
                session.setExpiresAt();
                return session.getMpBlogUser();
            }
        }
        return null;
    }
}
