package com.example.mpblog.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
public class MPBlogEntry {

    @Id
    @GeneratedValue
    private int id;

    @Length(min = 3, max = 50)
    private String title;

    @Length(min = 1, max = 65000)
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

    public String getShortContent() {
        String[] shortContentArray = content.split("(\r\n|\n)");
        return shortContentArray[0];
    }

    public String getPicture() {
        return "blog_" + this.id + ".jpeg";
    }

    public void setPicture(MultipartFile file) {
        String rootLocation = System.getProperty("user.dir");
        Path destinationFile = Paths.get("src/main/resources/static/images/blogpost/blog_" + this.id + ".jpeg");
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MPBlogComment> getMpBlogComments() {
        return mpBlogComments;
    }

}
