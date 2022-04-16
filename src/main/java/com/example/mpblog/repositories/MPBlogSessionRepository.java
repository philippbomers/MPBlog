package com.example.mpblog.repositories;

import com.example.mpblog.entities.MPBlogSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MPBlogSessionRepository extends CrudRepository<MPBlogSession, String> {
    Optional<MPBlogSession> findById(String sessionId);

    Optional<MPBlogSession> findMPBlogUserById(String sessionId);
}
