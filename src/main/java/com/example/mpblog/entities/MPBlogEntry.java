package com.example.mpblog.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

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
    @Column(columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    private Date date;

    @ManyToOne
    private MPBlogUser mpBlogUser;

    @OneToMany(mappedBy = "mpBlogEntry")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<MPBlogComment> mpBlogComments;

    public MPBlogEntry() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getUsername() {
        return this.getMpBlogUser().getUserName();
    }

    public List<MPBlogComment> getMpBlogComments() {
        return mpBlogComments.stream().sorted(Comparator.comparing(MPBlogComment::getDate)).toList();
    }

    public void setMpBlogComments(List<MPBlogComment> mpBlogComments) {
        this.mpBlogComments = mpBlogComments;
    }

    public String getShortContent() {
        String[] shortContentArray = content.split("(\r\n|\n)");
        return shortContentArray[0];
    }
}
