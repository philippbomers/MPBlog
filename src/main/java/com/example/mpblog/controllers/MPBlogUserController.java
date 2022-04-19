package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.services.MPBlogCommentService;
import com.example.mpblog.services.MPBlogEntryService;
import com.example.mpblog.services.MPBlogSessionService;
import com.example.mpblog.services.MPBlogUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Controller class for managing the users
 * Uses the user, session, entry and comment services
 */
@Controller
public class MPBlogUserController {

    private final MPBlogUserService mpBlogUserService;
    private final MPBlogSessionService mpBlogSessionService;

    private final MPBlogEntryService mpBlogEntryService;

    private final MPBlogCommentService mpBlogCommentService;

    public MPBlogUserController(MPBlogUserService mpBlogUserService,
                                MPBlogSessionService mpBlogSessionService,
                                MPBlogEntryService mpBlogEntryService,
                                MPBlogCommentService mpBlogCommentService) {

        this.mpBlogUserService = mpBlogUserService;
        this.mpBlogSessionService = mpBlogSessionService;
        this.mpBlogEntryService = mpBlogEntryService;
        this.mpBlogCommentService = mpBlogCommentService;
    }

    /**
     * Getmapping method for registering a new user
     * @param model used to transfer data from model to view through the controller
     * @return the html data holding the registration info
     */
    @GetMapping("/newUser")
    public String newUser(Model model) {

        model.addAttribute("mpBlogUser", new MPBlogUser());
        return "new/newUser";
    }

    /**
     * Postmapping method for registering a new user
     * @param mpBlogUser is the user that has been created through registration form
     * @param bindingResult set of rules that the data should follow
     * @return to the registration form when the conditions are not met, saves the registered user and returns to
     * the successful registration page otherwise
     */
    @PostMapping("/newUser")
    public String newUserResult(@Valid @ModelAttribute("mpBlogUser") MPBlogUser mpBlogUser,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "new/newUser";
        }

        this.mpBlogUserService.addMPBlogUser(mpBlogUser);
        return "helpers/registerSuccessfully";
    }

    /**
     * Getmapping method to display the users as a list
     *
     * @param model used to transfer data from model to view through the controller
     * @return the view data which shows the list of users given through the model
     */
    @GetMapping("/listUser")
    public String listUser(Model model) {

        model.addAttribute("mpBlogUsers", this.mpBlogUserService.getMPBlogUsers());
        return "list/listUser";
    }

    /**
     * Getmapping method to change admin status of a certain user. (Demote/Promote)
     * Is only available to the users that already have the admin status
     *
     * @param id of the user subject to the change
     * @param model used to transfer data from model to view through the controller
     * @return to the user list view after changing admin status of the user. Transfers the actual user
     * list to the view after updating it
     */
    @GetMapping("/switchAdminRights/{id}/listUser")
    public String switchAdminRights(@PathVariable int id,
                                    Model model) {

        this.mpBlogUserService.
                changeUserAdminStatus(this.mpBlogUserService.
                        getMPBlogUser(id));

        model.addAttribute("mpBlogUsers", this.mpBlogUserService.getMPBlogUsers());
        return "redirect:/listUser";
    }

    /**
     * Getmapping method to view a certain user
     *
     * @param sessionId if of the current session
     * @param model used to transfer data from model to view through the controller
     * @return the user information (name, number of entries and comments, etc...) if there is an active session,
     * redirects to the homepage otherwise
     */
    @GetMapping("/showUser")
    public String showUser(@CookieValue(name = "sessionId", required = false) String sessionId,
                           Model model) {

        Optional<MPBlogUser> mpBlogUser = Optional.ofNullable(this.mpBlogSessionService.findById(sessionId).get().getMpBlogUser());

        model.addAttribute("numberOfEntries", this.mpBlogEntryService.countByMpBlogUser(mpBlogUser));
        model.addAttribute("numberOfComments", this.mpBlogCommentService.countByMpBlogUser(mpBlogUser));


        return sessionId == null ||
                sessionId.isEmpty() ?
                "redirect:/" :
                "show/showUser";
    }
}
