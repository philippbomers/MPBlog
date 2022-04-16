package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogComment;
import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.services.MPBlogCommentService;
import com.example.mpblog.services.MPBlogEntryService;
import com.example.mpblog.services.MPBlogSessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Optional;

@Controller
public class MPBlogCommentController {
    private final MPBlogCommentService mpBlogCommentService;
    private final MPBlogEntryService mpBlogEntryService;
    private final MPBlogSessionService mpBlogSessionService;

    public MPBlogCommentController(MPBlogCommentService mpBlogCommentService, MPBlogEntryService mpBlogEntryService, MPBlogSessionService mpBlogSessionService) {
        this.mpBlogCommentService = mpBlogCommentService;
        this.mpBlogEntryService = mpBlogEntryService;
        this.mpBlogSessionService = mpBlogSessionService;
    }

    @GetMapping("/{id}/addcomment")
    public String entryDetails(Model model, @PathVariable int id) {
        model.addAttribute("entryId", id);
        model.addAttribute("comment", new MPBlogComment());
        return "commentform";
    }

    @PostMapping("/{id}/addcomment")
    public String message(@CookieValue(value = "sessionId", defaultValue = "") String sessionId, @Valid @ModelAttribute("comment") MPBlogComment comment, BindingResult bindingResult, @PathVariable int id) {
        Optional<MPBlogEntry> blogEntry = this.mpBlogEntryService.getMPBlogEntry(id);
        Optional<MPBlogSession> session = this.mpBlogSessionService.findByIdAndExpiresAtAfter(sessionId, Instant.now());
        if (bindingResult.hasErrors() ||
                blogEntry.isEmpty() ||
                session.isEmpty()) {
            return "commentform";
        }
        comment.setMpBlogEntry(blogEntry.get());
        comment.setMpBlogUser(session.get().getMpBlogUser());
        mpBlogCommentService.save(comment);

        return "redirect:/" + id + "/entrydetails";
    }
}
