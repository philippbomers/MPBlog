package com.example.mpblog.entities;

import jdk.jfr.BooleanFlag;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.List;

/**
 * Entity class that holds the users, their relation with other entity classes and their binding conditions
 * The class is only responsible for standard getter and setter methods
 */
@Entity
public class MPBlogUser {

    @Id
    @GeneratedValue
    private int id;

    @Length(min = 3, max = 30)
    private String userName;

    @BooleanFlag
    private boolean adminRights;

    @Length(min = 6, max = 30)
    private String password;

    @OneToMany(mappedBy = "mpBlogUser")
    private List<MPBlogEntry> mpBlogEntryList;
    @OneToMany(mappedBy = "mpBlogUser")
    private Collection<MPBlogEntry> mpBlogEntry;

    public MPBlogUser() {
    }

    public int getId() {
        return this.id;
    }


    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdminRights() {
        return this.adminRights;
    }

    public void setAdminRights(boolean adminRights) {
        this.adminRights = adminRights;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<MPBlogEntry> getMpBlogEntry() {
        return this.mpBlogEntry;
    }

    public void setMpBlogEntry(Collection<MPBlogEntry> mpBlogEntry) {
        this.mpBlogEntry = mpBlogEntry;
    }

    public List<MPBlogEntry> getMpBlogEntryList() {
        return this.mpBlogEntryList;
    }

    public void setMpBlogEntryList(List<MPBlogEntry> mpBlogEntryList) {
        this.mpBlogEntryList = mpBlogEntryList;
    }
}
