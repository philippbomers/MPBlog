package com.example.mpblog;

import javax.persistence.*;
import java.util.Date;

@Entity
public class MPBlogEntry {

    @Id
    @GeneratedValue
    private int id;

    private String title;

    private String content;

    private Date date;

    @ManyToOne
    private MPBlogUser mpBlogUser;

    public MPBlogEntry() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
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
}
