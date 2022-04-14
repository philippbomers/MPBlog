package com.example.mpblog.repositories;

import com.example.mpblog.entities.MPBlogComment;
import com.example.mpblog.entities.MPBlogEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MPBlogCommentRepository extends CrudRepository<MPBlogComment, Integer> {
    List<MPBlogComment> findAll();

    MPBlogComment findById(int id);

    List<MPBlogComment> findByMpBlogEntry(MPBlogEntry blogEntry);
}
