package com.example.mpblog;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MPBlogService {

    private final MPBlogEntryRepository mpBlogEntryRepository;
    private final MPBlogUserRepository mpBlogUserRepository;


    public MPBlogService(MPBlogEntryRepository mpBlogEntryRepository, MPBlogUserRepository mpBlogUserRepository) {
        this.mpBlogEntryRepository = mpBlogEntryRepository;
        this.mpBlogUserRepository = mpBlogUserRepository;
    }

    public void addMPBlogEntry(MPBlogEntry mpBlogEntry) {
        this.mpBlogEntryRepository.save(mpBlogEntry);
    }

    public void addMPBlogEntry(MPBlogUser mpBlogUser) {
        this.mpBlogUserRepository.save(mpBlogUser);
    }

    public List<MPBlogEntry> getMPBlogEntry() {
        List<MPBlogEntry> result = this.mpBlogEntryRepository.findAll();
        return result.stream().sorted(Comparator.comparing(MPBlogEntry::getDate).reversed()).toList();
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

    public MPBlogUser getMPBlogUser(String username) {
        return this.mpBlogUserRepository.findByUserName(username);
    }
}
