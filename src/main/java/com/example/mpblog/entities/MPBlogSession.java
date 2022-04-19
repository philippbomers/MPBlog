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

    public String getId() {
        return this.id;
    }

    public MPBlogUser getMpBlogUser() {
        return this.mpBlogUser;
    }

    public void setMpBlogUser(MPBlogUser mpBlogUser) {
        this.mpBlogUser = mpBlogUser;
    }

    public Instant getExpiresAt() {
        return this.expiresAt;
    }

    public void setExpiresAt() {
        this.expiresAt = Instant.now().plusSeconds(7 * 24 * 60 * 60);
    }
}
