package com.example.mpblog.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MPBlogCategory {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<MPBlogEntry> entries = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MPBlogEntry> getEntries() {
        return entries;
    }

    public void setEntries(Set<MPBlogEntry> entries) {
        this.entries = entries;
    }
}
