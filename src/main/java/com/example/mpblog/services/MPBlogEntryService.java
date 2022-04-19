package com.example.mpblog.services;

import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.repositories.MPBlogEntryRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * Service class (record) for the entry class.
 *
 * @param mpBlogEntryRepository is used to access the necessary database queries for entries
 */
@Service
public record MPBlogEntryService(MPBlogEntryRepository mpBlogEntryRepository) {

    public void addMPBlogEntry(MPBlogEntry mpBlogEntry) {
        this.mpBlogEntryRepository.save(mpBlogEntry);
    }

    /**
     * Modified version of the get method for entries. Ensures that entries are sorted in reverse order
     * with respect to their creation date
     * @return the reversed list of all entries in database
     */
    public List<MPBlogEntry> getMPBlogEntry() {
        List<MPBlogEntry> result = this.mpBlogEntryRepository.findAll();
        return result.stream().sorted(Comparator.comparing(MPBlogEntry::getDate).reversed()).toList();
    }

    public Optional<MPBlogEntry> getMPBlogEntry(Integer id) {
        return this.mpBlogEntryRepository.findById(id);
    }

    /**
     * Method to create a map of short versions of the entry contents to be used in list views, mapped by entry id.
     *
     * @param entryList the list of entries to be processed
     * @return a hash map of content indexed through entry ids. If the original text consists of only one paragraph,
     * it would not be shortened and will be kept as it is.
     */
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

    /**
     * Method returns the number of entries written by a certain user
     * @param mpBlogUser the user is taken as query parameter
     * @return total number of entries of the user
     */
    public int countByMpBlogUser(Optional<MPBlogUser> mpBlogUser) {
        return this.mpBlogEntryRepository.countByMpBlogUser(mpBlogUser);
    }
}
