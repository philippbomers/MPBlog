package com.example.mpblog.repositories;

import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.entities.MPBlogUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface MPBlogSessionRepository extends CrudRepository<MPBlogSession, String> {

    Optional<MPBlogSession> findByIdAndExpiresAtAfter(String sessionId, Instant instant);
    MPBlogUser findMPBlogUserByMpBlogUserId(int id);

}
