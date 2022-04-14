package com.example.mpblog;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "mpBlogEntry")
    private List<MPBlogComment> mpBlogComments;

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

    public String getUsername() {
        return this.getMpBlogUser().getUserName();
    }

    public List<MPBlogComment> getMpBlogComments() {
        return mpBlogComments;
    }

    public void setMpBlogComments(List<MPBlogComment> mpBlogComments) {
        this.mpBlogComments = mpBlogComments;
    }
}
