package com.example.mpblog.entities;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class MPBlogComment {

    @Id
    @GeneratedValue
    private int id;

    @Min(3)
    private String userComment;

    @CreatedDate
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

    public String getRealDate() {
        SimpleDateFormat DateFor = new SimpleDateFormat("dd.MM.yyyy");
        return DateFor.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
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
