package com.example.mpblog.repositories;

import com.example.mpblog.entities.MPBlogEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MPBlogEntryRepository extends CrudRepository<MPBlogEntry, Integer> {

    List<MPBlogEntry> findAll();

    MPBlogEntry findById(int id);
}
