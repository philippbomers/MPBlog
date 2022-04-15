package com.example.mpblog.services;

import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.entities.MPBlogUser;
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
        mpBlogSessionRepository.save(mpBlogSession);
    }

    public Optional<MPBlogSession> findByIdAndExpiresAtAfter(String sessionId, Instant instant) {
        return mpBlogSessionRepository.findByIdAndExpiresAtAfter(sessionId, instant);
    }

    public MPBlogUser findMPBlogUserByMpBlogUserId(int id) {
        return mpBlogSessionRepository.findMPBlogUserByMpBlogUserId(id);
    }
}
