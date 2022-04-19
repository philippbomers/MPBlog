package com.example.mpblog.services;

import com.example.mpblog.entities.MPBlogComment;
import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.repositories.MPBlogCommentRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Service class (record) for the comment class.
 *
 * @param mpBlogCommentRepository is used to access the necessary database queries for comments
 */
@Service
public record MPBlogCommentService(MPBlogCommentRepository mpBlogCommentRepository) {

    public void addMPBlogComment(MPBlogComment mpBlogComment) {
        this.mpBlogCommentRepository.save(mpBlogComment);
    }

    /**
     * Modified version of the get method for comments. Ensures that comments are sorted in reverse order
     * with respect to their creation date
     * @return the reversed list of all comments in database
     */
    public List<MPBlogComment> getMPBlogComment() {

        List<MPBlogComment> result = this.mpBlogCommentRepository.findAll();

        return result.
                stream().
                sorted(Comparator.comparing(MPBlogComment::getDate).
                        reversed()).
                toList();
    }

    public MPBlogComment getMPBlogComment(int id) {
        return this.mpBlogCommentRepository.findById(id);
    }

    public List<MPBlogComment> getMPBlogComment(MPBlogEntry mpBlogEntry) {
        return this.mpBlogCommentRepository.findByMpBlogEntry(mpBlogEntry);
    }

    public void save(MPBlogComment mpBlogComment) {
        mpBlogCommentRepository.save(mpBlogComment);
    }

    public void delete(MPBlogComment comment) {
        this.mpBlogCommentRepository.delete(comment);
    }

    public MPBlogComment findById(int id) {
        return this.mpBlogCommentRepository.findById(id);
    }

    /**
     * Method returns the number of comments written by a certain user
     * @param mpBlogUser the user is taken as query parameter
     * @return total number of comments of the user
     */
    public int countByMpBlogUser(Optional<MPBlogUser> mpBlogUser) {
        return this.mpBlogCommentRepository.countByMpBlogUser(mpBlogUser);
    }
}
