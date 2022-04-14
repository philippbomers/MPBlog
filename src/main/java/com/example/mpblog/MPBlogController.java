package com.example.mpblog;

import com.example.mpblog.entities.MPBlogUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class MPBlogController {

    private final MPBlogService mpBlogService;

    @Autowired
    public MPBlogController(MPBlogService mpBlogService) {
        this.mpBlogService = mpBlogService;
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
            this.mpBlogService.addMPBlogUser(mpBlogUser);
            return "registersuccessfully";
        }
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
