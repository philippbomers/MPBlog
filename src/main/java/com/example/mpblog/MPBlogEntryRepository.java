package com.example.mpblog;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MPBlogEntryRepository extends CrudRepository<MPBlogEntry, Integer> {

    List<MPBlogEntry> findAll();

    MPBlogEntry findById(int id);
}
