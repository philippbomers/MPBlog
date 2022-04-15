package com.example.mpblog.entities;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Entity
public class MPBlogEntry {

    @Id
    @GeneratedValue
    private int id;

    @Length(min = 3)
    private String title;

    @Length(min = 3)
    private String content;

    @CreatedDate
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
        return mpBlogComments.stream().sorted(Comparator.comparing(MPBlogComment::getDate).reversed()).toList();
    }

    public void setMpBlogComments(List<MPBlogComment> mpBlogComments) {
        this.mpBlogComments = mpBlogComments;
    }
}