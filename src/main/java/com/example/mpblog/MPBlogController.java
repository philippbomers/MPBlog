package com.example.mpblog;

import com.example.mpblog.entities.MPBlogUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String register(@Valid @ModelAttribute MPBlogUser mpBlogUser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mpBlogUser", mpBlogUser);
            return "registerdialog";
        } else {
            this.mpBlogService.addMPBlogUser(mpBlogUser);
            model.addAttribute("mpBlogUser", mpBlogUser);
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
