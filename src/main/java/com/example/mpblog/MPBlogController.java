package com.example.mpblog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MPBlogController {

    private final MPBlogService mpBlogService;

    public MPBlogController(MPBlogService mpBlogService) {
        this.mpBlogService = mpBlogService;
    }

    @GetMapping("/loginform")
    public String loginForm(Model model){
        model.addAttribute("user",new MPBlogUser());
        return "loginform";
    }

    @GetMapping("/showentries")
    public String showEntries(Model model){
        model.addAttribute("entries", mpBlogService.getMPBlogEntry());
        return "showentries";
    }


}
