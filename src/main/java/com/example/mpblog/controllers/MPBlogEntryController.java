package com.example.mpblog.controllers;

import com.example.mpblog.services.MPBlogEntryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        model.addAttribute("entry", this.mpBlogEntryService.getMPBlogEntry(id));
        return "entrydetails";
    }
}
