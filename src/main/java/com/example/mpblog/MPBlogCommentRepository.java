package com.example.mpblog;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MPBlogCommentRepository extends CrudRepository<MPBlogComment, Integer> {
    List<MPBlogComment> findAll();
    MPBlogComment findById(int id);
    List<MPBlogComment> findByMpBlogEntry(MPBlogEntry blogEntry);
}
