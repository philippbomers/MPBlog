package com.example.mpblog.services;

import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.repositories.MPBlogEntryRepository;
import org.springframework.stereotype.Service;

import java.io.File;
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

        HashMap<Integer, String> result = new HashMap<>();

        entryList.forEach(blogEntry -> {
            result.
                    put(blogEntry.getId(), Objects.equals(blogEntry.getContent(), blogEntry.getShortContent()) ?
                            blogEntry.getContent() :
                            blogEntry.getShortContent() + " [...]");
        });

        return result;
    }

    public void updateTitle(int id, String title) {
        this.mpBlogEntryRepository.updateTitle(id, title);
    }

    public void updateContent(int id, String content) {
        this.mpBlogEntryRepository.updateContent(id, content);
    }

    public List<MPBlogEntry> findAll() {
        return this.mpBlogEntryRepository.findAll();
    }

    public void save(MPBlogEntry entry) {
        this.mpBlogEntryRepository.save(entry);
    }

    public MPBlogEntry findById(int id) {
        return this.mpBlogEntryRepository.findById(id);
    }

    public void delete(MPBlogEntry entry) {

        this.mpBlogEntryRepository.delete(entry);

        File file = new File("src/main/resources/static/images/blogpost/blog_" + entry.getId() + ".jpeg");
        file.delete();
    }
}
