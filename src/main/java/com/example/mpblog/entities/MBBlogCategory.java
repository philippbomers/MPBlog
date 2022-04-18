package com.example.mpblog.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MBBlogCategory {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    private MPBlogCategoryOption MPBlogCategoryOption;

    @ManyToMany(mappedBy = "categories")
    private Set<MPBlogEntry> entries = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MPBlogCategoryOption getCategoryOption() {
        return MPBlogCategoryOption;
    }

    public void setCategoryOption(MPBlogCategoryOption MPBlogCategoryOption) {
        this.MPBlogCategoryOption = MPBlogCategoryOption;
    }

    public Set<MPBlogEntry> getEntries() {
        return entries;
    }

    public void setEntries(Set<MPBlogEntry> entries) {
        this.entries = entries;
    }
}
