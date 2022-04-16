package com.example.mpblog.services;

import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.repositories.MPBlogSessionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class MPBlogSessionService {

    private final MPBlogSessionRepository mpBlogSessionRepository;

    public MPBlogSessionService(MPBlogSessionRepository mpBlogSessionRepository) {
        this.mpBlogSessionRepository = mpBlogSessionRepository;
    }

    public void save(MPBlogSession mpBlogSession) {
        this.mpBlogSessionRepository.save(mpBlogSession);
    }

    public Optional<MPBlogSession> findByIdAndExpiresAtAfter(String sessionId) {
        return this.mpBlogSessionRepository.findByIdAndExpiresAtAfter(sessionId, Instant.now().plusSeconds(7 * 24 * 60 * 60));
    }
}
