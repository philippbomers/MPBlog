package com.example.mpblog.repositories;

import com.example.mpblog.entities.MPBlogComment;
import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.entities.MPBlogUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for the comment class.
 * Responsible for query methods that are required and used through comment service
 */
@Repository
public interface MPBlogCommentRepository extends CrudRepository<MPBlogComment, Integer> {

    List<MPBlogComment> findAll();

    MPBlogComment findById(int id);

    List<MPBlogComment> findByMpBlogEntry(MPBlogEntry blogEntry);

    int countByMpBlogUser(Optional<MPBlogUser> mpBlogUser);
}
