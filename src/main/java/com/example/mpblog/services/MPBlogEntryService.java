package com.example.mpblog.services;

import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.repositories.MPBlogEntryRepository;
import org.springframework.stereotype.Service;

import java.util.*;

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
        HashMap<Integer, String> result = new HashMap<Integer, String>();
        for (MPBlogEntry blogEntry : entryList) {
            if (Objects.equals(blogEntry.getContent(), blogEntry.getShortContent())) {
                result.put(blogEntry.getId(), blogEntry.getContent());
            } else {
                result.put(blogEntry.getId(), blogEntry.getShortContent() + " [...]");
            }
        }
        return result;
    }

}
