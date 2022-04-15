package com.example.mpblog.services;

import com.example.mpblog.entities.MPBlogComment;
import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.repositories.MPBlogCommentRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MPBlogCommentService {
    private final MPBlogCommentRepository mpBlogCommentRepository;

    public MPBlogCommentService(MPBlogCommentRepository mpBlogCommentRepository) {
        this.mpBlogCommentRepository = mpBlogCommentRepository;
    }

    public void addMPBlogComment(MPBlogComment mpBlogComment) {
        this.mpBlogCommentRepository.save(mpBlogComment);
    }

    public List<MPBlogComment> getMPBlogComment() {
        List<MPBlogComment> result = this.mpBlogCommentRepository.findAll();
        return result.stream().sorted(Comparator.comparing(MPBlogComment::getDate).reversed()).toList();
    }

    public MPBlogComment getMPBlogComment(int id) {
        return this.mpBlogCommentRepository.findById(id);
    }

    public List<MPBlogComment> getMPBlogComment(MPBlogEntry mpBlogEntry) {
        return this.mpBlogCommentRepository.findByMpBlogEntry(mpBlogEntry);
    }
}