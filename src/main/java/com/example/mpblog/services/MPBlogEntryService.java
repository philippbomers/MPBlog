package com.example.mpblog.services;

import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.repositories.MPBlogEntryRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MPBlogEntryService {

    private final MPBlogEntryRepository mpBlogEntryRepository;

    public MPBlogEntryService(MPBlogEntryRepository mpBlogEntryRepository) {
        this.mpBlogEntryRepository = mpBlogEntryRepository;
    }

    public void addMPBlogEntry(MPBlogEntry mpBlogEntry) {
        this.mpBlogEntryRepository.save(mpBlogEntry);
    }

    public List<MPBlogEntry> getMPBlogEntry() {
        List<MPBlogEntry> result = this.mpBlogEntryRepository.findAll();
        return result.stream().sorted(Comparator.comparing(MPBlogEntry::getDate).reversed()).toList();
    }

    public MPBlogEntry getMPBlogEntry(int id) {
        return this.mpBlogEntryRepository.findById(id);
    }
}
