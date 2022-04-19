package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogComment;
import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.services.MPBlogCommentService;
import com.example.mpblog.services.MPBlogEntryService;
import com.example.mpblog.services.MPBlogSessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MPBlogCommentController {
    private final MPBlogCommentService mpBlogCommentService;
    private final MPBlogEntryService mpBlogEntryService;
    private final MPBlogSessionService mpBlogSessionService;

    public MPBlogCommentController(
            MPBlogCommentService mpBlogCommentService,
            MPBlogEntryService mpBlogEntryService,
            MPBlogSessionService mpBlogSessionService
    ) {
        this.mpBlogCommentService = mpBlogCommentService;
        this.mpBlogEntryService = mpBlogEntryService;
        this.mpBlogSessionService = mpBlogSessionService;
    }

    @GetMapping("/{id}/newcomment")
    public String entryDetails(Model model, @PathVariable int id) {
        model.addAttribute("entryId", id);
        model.addAttribute("comment", new MPBlogComment());
        return "newComment";
    }

    @PostMapping("/{id}/newcomment")
    public String message(@CookieValue(name = "sessionId") String sessionId, @Valid @ModelAttribute("comment") MPBlogComment comment, BindingResult bindingResult, @PathVariable int id, Model model) {
        Optional<MPBlogEntry> blogEntry = this.mpBlogEntryService.getMPBlogEntry(id);
        Optional<MPBlogUser> mpBlogUser = this.mpBlogSessionService.findMPBlogUserById(sessionId);
        if (bindingResult.hasErrors() || blogEntry.isEmpty() || mpBlogUser.isEmpty()) {
            return "newComment";
        }
        comment.setMpBlogEntry(blogEntry.get());
        comment.setMpBlogUser(mpBlogUser.get());
        this.mpBlogCommentService.save(comment);
        model.addAttribute("entry", blogEntry.get());
        return "redirect:/" + id + "/showentry";
    }

    @GetMapping("/{id}/deleteComment")
    public String delete(@CookieValue(value = "sessionId", defaultValue = "") String sessionId, @PathVariable int id) {
        MPBlogComment comment = this.mpBlogCommentService.findById(id);
        Optional<MPBlogSession> optionalSession = this.mpBlogSessionService.findById(sessionId);
        if (optionalSession.isPresent() && (
                comment.getMpBlogUser() == optionalSession.get().getMpBlogUser()) ||
                optionalSession.get().getMpBlogUser().isAdminRights()) {
            int newID = comment.getMpBlogEntry().getId();
            this.mpBlogCommentService.delete(comment);
            return "redirect:/" + newID + "/showentry";
        }
        throw new IllegalArgumentException("User is not authorized to delete this comment!");
    }
}
