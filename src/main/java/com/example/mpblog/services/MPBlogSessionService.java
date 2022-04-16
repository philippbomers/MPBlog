package com.example.mpblog.services;

import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.repositories.MPBlogSessionRepository;
import org.springframework.stereotype.Service;

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

    public Optional<MPBlogSession> findById(String sessionId) {
        return this.mpBlogSessionRepository.findById(sessionId);
    }

    public Optional<MPBlogUser> findMPBlogUserById(String sessionId) {
        Optional<MPBlogSession> mpBlogSession = this.findById(sessionId);
        return mpBlogSession.map(MPBlogSession::getMpBlogUser);
    }
}
