package com.example.mpblog;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MPBlogEntryRepository extends CrudRepository<MPBlogEntry, Integer> {
    @Override
    default List<MPBlogEntry> findAll(){
        return findAll(Sort.by(Sort.Direction.DESC, "date"));
    }
    List<MPBlogEntry> findAll(Sort date);
    MPBlogEntry findById(int id);
    List<MPBlogEntry> sortByDate();
}
