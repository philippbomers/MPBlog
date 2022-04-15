package com.example.mpblog.controllers;

import com.example.mpblog.services.MPBlogCommentService;
import org.springframework.stereotype.Controller;

@Controller
public class MPBlogCommentController {
    private final MPBlogCommentService mpBlogCommentService;

    public MPBlogCommentController(MPBlogCommentService mpBlogCommentService) {
        this.mpBlogCommentService = mpBlogCommentService;
    }
}
