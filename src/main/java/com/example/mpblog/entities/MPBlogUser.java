package com.example.mpblog.entities;

import jdk.jfr.BooleanFlag;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.List;

@Entity
public class MPBlogUser {

    @Id
    @GeneratedValue
    private int id;

    @UniqueElements
    @Min(3)
    private String userName;

    @BooleanFlag
    private boolean adminRights;

    @Min(6)
    private String password;

    @OneToMany(mappedBy = "mpBlogUser")
    private List<MPBlogEntry> mpBlogEntryList;
    @OneToMany(mappedBy = "mpBlogUser")
    private Collection<MPBlogEntry> mpBlogEntry;

    public MPBlogUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdminRights() {
        return adminRights;
    }

    public void setAdminRights(boolean adminRights) {
        this.adminRights = adminRights;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<MPBlogEntry> getMpBlogEntry() {
        return mpBlogEntry;
    }

    public void setMpBlogEntry(Collection<MPBlogEntry> mpBlogEntry) {
        this.mpBlogEntry = mpBlogEntry;
    }

    public List<MPBlogEntry> getMpBlogEntryList() {
        return mpBlogEntryList;
    }

    public void setMpBlogEntryList(List<MPBlogEntry> mpBlogEntryList) {
        this.mpBlogEntryList = mpBlogEntryList;
    }
}
