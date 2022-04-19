package com.example.mpblog.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Entity class that holds the comments, their relation with other entity classes and their binding conditions
 * The class is only responsible for standard getter and setter methods
 */
@Entity
public class MPBlogComment {

    @Id
    @GeneratedValue
    private int id;

    @Length(min = 1, max = 65000)
    @Column(columnDefinition = "TEXT")
    private String userComment;

    @CreationTimestamp
    private Date date;

    @ManyToOne
    private MPBlogUser mpBlogUser;

    @ManyToOne
    private MPBlogEntry mpBlogEntry;

    public MPBlogComment() {
    }

    public int getId() {
        return this.id;
    }

    public String getUserComment() {
        return this.userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public Date getDate() {
        return this.date;
    }

    public String getRealDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(this.date);
    }

    public MPBlogUser getMpBlogUser() {
        return this.mpBlogUser;
    }

    public void setMpBlogUser(MPBlogUser mpBlogUser) {
        this.mpBlogUser = mpBlogUser;
    }

    public MPBlogEntry getMpBlogEntry() {
        return this.mpBlogEntry;
    }

    public void setMpBlogEntry(MPBlogEntry mpBlogEntry) {
        this.mpBlogEntry = mpBlogEntry;
    }
}
