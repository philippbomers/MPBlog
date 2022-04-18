package com.example.mpblog.services;

import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.repositories.MPBlogEntryRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public record MPBlogEntryService(MPBlogEntryRepository mpBlogEntryRepository) {

    public void addMPBlogEntry(MPBlogEntry mpBlogEntry) {
        this.mpBlogEntryRepository.save(mpBlogEntry);
    }

    public List<MPBlogEntry> getMPBlogEntry() {
        List<MPBlogEntry> result = this.mpBlogEntryRepository.findAll();
        return result.stream().sorted(Comparator.comparing(MPBlogEntry::getDate).reversed()).toList();
    }

    public Optional<MPBlogEntry> getMPBlogEntry(Integer id) {
        return this.mpBlogEntryRepository.findById(id);
    }

    public HashMap<Integer, String> mapTheShortContent(List<MPBlogEntry> entryList) {
        HashMap result = new HashMap<Integer, String>();
        for (MPBlogEntry blogEntry : entryList) {
            result.put(blogEntry.getId(), blogEntry.getShortContent());
        }
        return result;
    }

}
