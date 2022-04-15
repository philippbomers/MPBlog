package com.example.mpblog;

import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.services.MPBlogCommentService;
import com.example.mpblog.services.MPBlogEntryService;
import com.example.mpblog.services.MPBlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class MPBlogController {

    private final MPBlogCommentService mpBlogCommentService;
    private final MPBlogEntryService mpBlogEntryService;
    private final MPBlogUserService mpBlogUserService;

    @Autowired
    public MPBlogController(MPBlogCommentService mpBlogCommentService, MPBlogEntryService mpBlogEntryService, MPBlogUserService mpBlogUserService) {
        this.mpBlogCommentService = mpBlogCommentService;
        this.mpBlogEntryService = mpBlogEntryService;
        this.mpBlogUserService = mpBlogUserService;
    }

    @GetMapping("/registerdialog")
    public String registerDialog(Model model) {
        model.addAttribute("mpBlogUser", new MPBlogUser());
        return "registerdialog";
    }

    @PostMapping("/registerdialog")
    public String register(@Valid @ModelAttribute("mpBlogUser") MPBlogUser mpBlogUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registerdialog";
        } else {
            this.mpBlogUserService.addMPBlogUser(mpBlogUser);
            return "registersuccessfully";
        }
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
