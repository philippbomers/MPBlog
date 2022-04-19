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

/**
 * Controller class for comments.
 * Uses comment, entry and session services.
 */
@Controller
public class MPBlogCommentController {
    private final MPBlogCommentService mpBlogCommentService;
    private final MPBlogEntryService mpBlogEntryService;
    private final MPBlogSessionService mpBlogSessionService;

    public MPBlogCommentController(MPBlogCommentService mpBlogCommentService,
                                   MPBlogEntryService mpBlogEntryService,
                                   MPBlogSessionService mpBlogSessionService) {

        this.mpBlogCommentService = mpBlogCommentService;
        this.mpBlogEntryService = mpBlogEntryService;
        this.mpBlogSessionService = mpBlogSessionService;
    }

    /**
     * GetMapping method to call the html data that is responsible for creating a new comment
     *
     *@param id path variable regarding the entry that the comment belongs to
     *@param model used to transfer data from model to view through the controller
     *
     * @return directory pad of the regarding html data
     */
    @GetMapping("/{id}/newComment")
    public String newComment(@PathVariable int id, Model model) {

        model.addAttribute("entryId", id);
        model.addAttribute("comment", new MPBlogComment());
        return "new/newComment";
    }

    /**
     * Postmapping method to check the user input through the comment creation template
     * Checks the user input and creates a new comment when the conditions are met
     *
     * @param sessionId id of the current session
     * @param comment the comment created through get mapping
     * @param bindingResult set of rules that the data should follow
     * @param id path variable regarding the entry that the comment belongs to
     * @return to the comment form, when the conditions are not met, to the related entry when the comment is
     * successfully created
     */
    public String newCommentSubmit(@CookieValue(name = "sessionId") String sessionId,
                                   @Valid @ModelAttribute("comment") MPBlogComment comment,
                                   BindingResult bindingResult,
                                   @PathVariable int id) {

        Optional<MPBlogEntry> blogEntry = this.mpBlogEntryService.getMPBlogEntry(id);
        Optional<MPBlogUser> mpBlogUser = this.mpBlogSessionService.findMPBlogUserById(sessionId);

        if (bindingResult.hasErrors() ||
                blogEntry.isEmpty() ||
                mpBlogUser.isEmpty()) {
            return "new/newComment";
        }

        comment.setMpBlogEntry(blogEntry.get());
        comment.setMpBlogUser(mpBlogUser.get());
        this.mpBlogCommentService.save(comment);

        return "redirect:/" + id + "/showEntry";
    }

    /**
     * Getmapping method to delete a certain comment
     *
     * @param sessionId id of the current session
     * @param id the id of the comment to be deleted
     * @return to the entry that the comment once belonged to
     */
    @GetMapping("/{id}/deleteComment")
    public String deleteComment(@CookieValue(value = "sessionId", defaultValue = "") String sessionId,
                                @PathVariable int id) {

        MPBlogComment comment = this.mpBlogCommentService.findById(id);

        Optional<MPBlogSession> optionalSession = this.mpBlogSessionService.findById(sessionId);

        if (optionalSession.isPresent() &&
                (comment.getMpBlogUser() == optionalSession.get().getMpBlogUser()) ||
                optionalSession.get().getMpBlogUser().isAdminRights()) {

            this.mpBlogCommentService.delete(comment);

            return "redirect:/" + comment.getMpBlogEntry().getId() + "/showEntry";
        }

        throw new IllegalArgumentException("User is not authorized to delete this comment!");
    }
}
