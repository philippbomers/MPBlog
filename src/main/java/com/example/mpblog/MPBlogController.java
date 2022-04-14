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

    @GetMapping("/loginForm")
    public String loginForm(Model model){
        model.addAttribute("emptyUser",new MPBlogUser());
        return "loginform";
    }

}
