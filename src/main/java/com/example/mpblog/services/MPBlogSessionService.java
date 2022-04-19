package com.example.mpblog.services;

import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.repositories.MPBlogSessionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class (record) for the session class.
 *
 * @param mpBlogSessionRepository is used to access the necessary database queries for sessions
 */
@Service
public record MPBlogSessionService(MPBlogSessionRepository mpBlogSessionRepository) {

    public void save(MPBlogSession mpBlogSession) {
        this.mpBlogSessionRepository.save(mpBlogSession);
    }

    public Optional<MPBlogSession> findById(String sessionId) {
        return this.mpBlogSessionRepository.findById(sessionId);
    }

    public Optional<MPBlogUser> findMPBlogUserById(String sessionId) {
        return this.
                findById(sessionId).
                map(MPBlogSession::getMpBlogUser);
    }

    public void delete(MPBlogSession entry) {
        this.mpBlogSessionRepository.delete(entry);
    }
}
