package com.example.mpblog.entities;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class MPBlogComment {

    @Id
    @GeneratedValue
    private int id;

    @Length(min = 3)
    private String userComment;

    @UpdateTimestamp
    private Date date;

    @ManyToOne
    private MPBlogUser mpBlogUser;

    @ManyToOne
    private MPBlogEntry mpBlogEntry;

    public MPBlogComment() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRealDate() {
        SimpleDateFormat DateFor = new SimpleDateFormat("dd.MM.yyyy");
        return DateFor.format(date);
    }

    public MPBlogUser getMpBlogUser() {
        return mpBlogUser;
    }

    public void setMpBlogUser(MPBlogUser mpBlogUser) {
        this.mpBlogUser = mpBlogUser;
    }

    public MPBlogEntry getMpBlogEntry() {
        return mpBlogEntry;
    }

    public void setMpBlogEntry(MPBlogEntry mpBlogEntry) {
        this.mpBlogEntry = mpBlogEntry;
    }
}
