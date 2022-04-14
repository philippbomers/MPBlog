package com.example.mpblog;

import com.example.mpblog.entities.MPBlogUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MPBlogController {

    private final MPBlogService mpBlogService;

    public MPBlogController(MPBlogService mpBlogService) {
        this.mpBlogService = mpBlogService;
    }

    @GetMapping("/registerdialog")
    public String loginForm(Model model) {
        model.addAttribute("user", new MPBlogUser());
        return "registerdialog";
    }

    @GetMapping("/showentries")
    public String showEntries(Model model) {
        model.addAttribute("entries", mpBlogService.getMPBlogEntry());
        return "showentries";
    }

    @GetMapping("{id}/entrydetails")
    public String entryDetails(Model model, @PathVariable int id) {
        model.addAttribute("entry", this.mpBlogService.getMPBlogEntry(id));
        return "entrydetails";
    }
}
