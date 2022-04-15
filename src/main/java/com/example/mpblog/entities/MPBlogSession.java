package com.example.mpblog.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.Instant;
import java.util.UUID;

@Entity
public class MPBlogSession {

    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    private MPBlogUser mpBlogUser;

    private Instant expiresAt;

    public MPBlogSession() {
    }

    public MPBlogSession(MPBlogUser mpBlogUser, Instant plusSeconds) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MPBlogUser getMpBlogUser() {
        return mpBlogUser;
    }

    public void setMpBlogUser(MPBlogUser mpBlogUser) {
        this.mpBlogUser = mpBlogUser;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}
