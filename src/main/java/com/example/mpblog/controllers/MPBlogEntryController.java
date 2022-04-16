package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.repositories.MPBlogEntryRepository;
import com.example.mpblog.services.MPBlogEntryService;
import com.example.mpblog.services.MPBlogSessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class MPBlogEntryController {
    private final MPBlogEntryService mpBlogEntryService;
    private final MPBlogEntryRepository mpBlogEntryRepository;

    private final MPBlogSessionService mpBlogSessionService;

    public MPBlogEntryController(MPBlogEntryService mpBlogEntryService, MPBlogEntryRepository mpBlogEntryRepository, MPBlogSessionService mpBlogSessionService) {
        this.mpBlogEntryService = mpBlogEntryService;
        this.mpBlogEntryRepository = mpBlogEntryRepository;
        this.mpBlogSessionService = mpBlogSessionService;
    }

    @GetMapping("/showentries")
    public String showEntries(Model model) {
        model.addAttribute("entries", this.mpBlogEntryService.getMPBlogEntry());
        return "showentries";
    }

    @GetMapping("{id}/entrydetails")
    public String entryDetails(Model model, @PathVariable int id) {
        Optional<MPBlogEntry> entry = this.mpBlogEntryService.getMPBlogEntry(id);
        if (entry.isPresent()) {
            model.addAttribute("entry", entry.get());
            return "entrydetails";
        }
        return "showentries";
    }

    @GetMapping("/{id}/deleteEntry")
    public String delete(@CookieValue(value = "sessionId", defaultValue = "") String sessionId, @PathVariable int id) {
        MPBlogEntry entry = mpBlogEntryRepository.findById(id);
        Optional<MPBlogSession> optionalSession = this.mpBlogSessionService.findById(sessionId);
        if (optionalSession.isPresent() &&
                (entry.getMpBlogUser() == optionalSession.get().getMpBlogUser() ||
                optionalSession.get().getMpBlogUser().isAdminRights())) {
            mpBlogEntryRepository.delete(entry);
            return "redirect:/showentries";
        }
        throw new IllegalArgumentException("User is not authorized to delete this entry!");

    }
}
