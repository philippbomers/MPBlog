package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.repositories.MPBlogEntryRepository;
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
    private final MPBlogSessionService mpBlogSessionService;

    public MPBlogEntryController(MPBlogEntryService mpBlogEntryService, MPBlogEntryRepository mpBlogEntryRepository, MPBlogSessionService mpBlogSessionService) {
        this.mpBlogEntryService = mpBlogEntryService;
        this.mpBlogEntryRepository = mpBlogEntryRepository;
        this.mpBlogSessionService = mpBlogSessionService;
    }

    @GetMapping("/newEntry")
    public String entry(Model model) {
        model.addAttribute("entry", new MPBlogEntry());
        return "new/newentry";
    }

    @PostMapping("/newEntry")
    public String message(@CookieValue(name = "sessionId") String sessionId, @Valid @ModelAttribute("entry") MPBlogEntry entry, BindingResult bindingResult) {
        Optional<MPBlogUser> mpBlogUser = this.mpBlogSessionService.findMPBlogUserById(sessionId);
        if (bindingResult.hasErrors() || mpBlogUser.isEmpty()) {
            return "new/newentry";
        }
        entry.setMpBlogUser(mpBlogUser.get());
        mpBlogEntryRepository.save(entry);
        return "redirect:/listentries";
    }

    @GetMapping({"/listentries", "/"})
    public String showEntries(Model model) {
        model.addAttribute("entries", this.mpBlogEntryService.getMPBlogEntry());
        return "list/listentries";
    }

    public String entryDetails(@PathVariable int id, Model model) {
        Optional<MPBlogEntry> entry = this.mpBlogEntryService.getMPBlogEntry(id);
        if (entry.isPresent()) {
            model.addAttribute("entry", entry.get());
            return "show/showentry";
        }
        return "redirect:/showentry";
    }

    @GetMapping("/{id}/deleteEntry")
    public String delete(@CookieValue(value = "sessionId", defaultValue = "") String sessionId, @PathVariable int id) {
        MPBlogEntry entry = mpBlogEntryRepository.findById(id);
        Optional<MPBlogSession> optionalSession = this.mpBlogSessionService.findById(sessionId);
        if (optionalSession.isPresent() &&
                (entry.getMpBlogUser() == optionalSession.get().getMpBlogUser() ||
                        optionalSession.get().getMpBlogUser().isAdminRights())) {
            mpBlogEntryRepository.delete(entry);
            return "redirect:/listentries";
        }
        throw new IllegalArgumentException("User is not authorized to delete this entry!");
    }

    @GetMapping("/{id}/editEntry")
    public String editEntryForm(Model model, @PathVariable int id) {
        model.addAttribute("entry", mpBlogEntryRepository.findById(id));
        return "update/editentry";
    }

    @PostMapping("/{id}/editEntry")
    public String updateEntry(@ModelAttribute("MPBlogEntry") MPBlogEntry mpBlogEntry, @PathVariable int id) {
        //update entry in the database
        mpBlogEntryRepository.updateTitle(id, mpBlogEntry.getTitle());
        mpBlogEntryRepository.updateContent(id, mpBlogEntry.getContent());
        return "redirect:/listentries";
    }
    /*@PostMapping("/{id}/editEntry")
    public String editEntry(@CookieValue(value = "sessionId", defaultValue = "") String sessionId, @PathVariable int id) {
        MPBlogEntry entry = mpBlogEntryRepository.findById(id);
        Optional<MPBlogSession> optionalSession = this.mpBlogSessionService.findById(sessionId);
        if (optionalSession.isPresent() &&
                (entry.getMpBlogUser() == optionalSession.get().getMpBlogUser() ||
                        optionalSession.get().getMpBlogUser().isAdminRights())) {
            mpBlogEntryRepository.delete(entry);
            return "redirect:/showentries";
        }
        throw new IllegalArgumentException("User is not authorized to delete this entry!");
    }*/
}
