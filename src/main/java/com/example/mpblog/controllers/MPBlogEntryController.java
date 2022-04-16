package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.repositories.MPBlogEntryRepository;
import com.example.mpblog.repositories.MPBlogSessionRepository;
import com.example.mpblog.services.MPBlogEntryService;
import com.example.mpblog.services.MPBlogSessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MPBlogEntryController {
    private final MPBlogEntryService mpBlogEntryService;
    private final MPBlogEntryRepository mpBlogEntryRepository;
    private final MPBlogSessionRepository mpBlogSessionRepository;
    private final MPBlogSessionService mpBlogSessionService;

    public MPBlogEntryController(MPBlogEntryService mpBlogEntryService, MPBlogEntryRepository mpBlogEntryRepository, MPBlogSessionRepository mpBlogSessionRepository, MPBlogSessionService mpBlogSessionService) {
        this.mpBlogEntryService = mpBlogEntryService;
        this.mpBlogEntryRepository = mpBlogEntryRepository;
        this.mpBlogSessionRepository = mpBlogSessionRepository;
        this.mpBlogSessionService = mpBlogSessionService;
    }
    @GetMapping("/newEntry")
    public String entry(Model model) {
        model.addAttribute("entry", new MPBlogEntry());
        return "newentry";
    }

    @PostMapping("/newEntry")
    public String message(@CookieValue(name = "sessionId") String sessionId, @Valid @ModelAttribute("entry") MPBlogEntry entry, BindingResult bindingResult) {
        Optional<MPBlogUser> mpBlogUser = this.mpBlogSessionService.findMPBlogUserById(sessionId);
        if (bindingResult.hasErrors() || mpBlogUser.isEmpty()) {
            return "newentry";
        }
        entry.setMpBlogUser(mpBlogUser.get());
        mpBlogEntryRepository.save(entry);
        return "redirect:/showentries";
    }

    @GetMapping("/showentries")
    public String showEntries(Model model) {
        model.addAttribute("entries", this.mpBlogEntryService.getMPBlogEntry());
        return "listentries";
    }

    @GetMapping("/{id}/entrydetails")
    public String entryDetails(@PathVariable int id, Model model) {
        Optional<MPBlogEntry> entry = this.mpBlogEntryService.getMPBlogEntry(id);
        if (entry.isPresent()) {
            model.addAttribute("entry", entry.get());
            return "showentry";
        }
        return "redirect:/showentries";
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
