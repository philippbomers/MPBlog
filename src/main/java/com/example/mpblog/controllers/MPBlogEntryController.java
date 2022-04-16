package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.services.MPBlogEntryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class MPBlogEntryController {
    private final MPBlogEntryService mpBlogEntryService;

    public MPBlogEntryController(MPBlogEntryService mpBlogEntryService) {
        this.mpBlogEntryService = mpBlogEntryService;
    }

    @GetMapping("/showentries")
    public String showEntries(Model model) {
        model.addAttribute("entries", this.mpBlogEntryService.getMPBlogEntry());
        return "showentries";
    }

    @GetMapping("{id}/entrydetails")
    public String entryDetails(Model model, @PathVariable int id) {
        Optional<MPBlogEntry> entry = this.mpBlogEntryService.getMPBlogEntry(id);
        if(entry.isPresent()){
            model.addAttribute("entry", entry.get());
            return "entrydetails";
        }
        return "showentries";
    }
}
