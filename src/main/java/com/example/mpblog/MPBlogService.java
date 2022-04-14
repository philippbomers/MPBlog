package com.example.mpblog;

import com.example.mpblog.entities.MPBlogComment;
import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.repositories.MPBlogCommentRepository;
import com.example.mpblog.repositories.MPBlogEntryRepository;
import com.example.mpblog.repositories.MPBlogUserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MPBlogService {

    private final MPBlogEntryRepository mpBlogEntryRepository;
    private final MPBlogUserRepository mpBlogUserRepository;

    private final MPBlogCommentRepository mpBlogCommentRepository;


    public MPBlogService(MPBlogEntryRepository mpBlogEntryRepository, MPBlogUserRepository mpBlogUserRepository, MPBlogCommentRepository mpBlogCommentRepository) {
        this.mpBlogEntryRepository = mpBlogEntryRepository;
        this.mpBlogUserRepository = mpBlogUserRepository;
        this.mpBlogCommentRepository = mpBlogCommentRepository;
    }

    public void addMPBlogEntry(MPBlogEntry mpBlogEntry) {
        this.mpBlogEntryRepository.save(mpBlogEntry);
    }

    public void addMPBlogEntry(MPBlogUser mpBlogUser) {
        this.mpBlogUserRepository.save(mpBlogUser);
    }

    public void addMPBlogComment(MPBlogComment mpBlogComment) {
        this.mpBlogCommentRepository.save(mpBlogComment);
    }

    public List<MPBlogEntry> getMPBlogEntry() {
        List<MPBlogEntry> result = this.mpBlogEntryRepository.findAll();
        return result.stream().sorted(Comparator.comparing(MPBlogEntry::getDate).reversed()).toList();
    }

    public List<MPBlogComment> getMPBlogComment() {
        List<MPBlogComment> result = this.mpBlogCommentRepository.findAll();
        return result.stream().sorted(Comparator.comparing(MPBlogComment::getDate).reversed()).toList();
    }

    public List<MPBlogUser> getMPBlogUser() {
        return this.mpBlogUserRepository.findAll();
    }

    public MPBlogEntry getMPBlogEntry(int id) {
        return this.mpBlogEntryRepository.findById(id);
    }

    public MPBlogUser getMPBlogUser(int id) {
        return this.mpBlogUserRepository.findById(id);
    }

    public MPBlogComment getMPBlogComment(int id) {
        return this.mpBlogCommentRepository.findById(id);
    }

    public MPBlogUser getMPBlogUser(String username) {
        return this.mpBlogUserRepository.findByUserName(username);
    }

    public List<MPBlogComment> getMPBlogComment(MPBlogEntry mpBlogEntry) {
        return this.mpBlogCommentRepository.findByMpBlogEntry(mpBlogEntry);
    }
}
