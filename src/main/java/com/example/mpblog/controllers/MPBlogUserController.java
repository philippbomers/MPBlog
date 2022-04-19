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

    @GetMapping("/newUser")
    public String newUser(Model model) {

        model.addAttribute("mpBlogUser", new MPBlogUser());
        return "new/newUser";
    }

    @PostMapping("/newUser")
    public String newUserResult(@Valid @ModelAttribute("mpBlogUser") MPBlogUser mpBlogUser,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "new/newUser";
        }

        this.mpBlogUserService.addMPBlogUser(mpBlogUser);
        return "helpers/registerSuccessfully";
    }

    @GetMapping("/listUser")
    public String listUser(Model model) {

        model.addAttribute("mpBlogUsers", this.mpBlogUserService.getMPBlogUsers());
        return "list/listUser";
    }

    @GetMapping("/switchAdminRights/{id}/listUser")
    public String switchAdminRights(@PathVariable int id,
                                    Model model) {

        this.mpBlogUserService.
                changeUserAdminStatus(this.mpBlogUserService.
                        getMPBlogUser(id));

        model.addAttribute("mpBlogUsers", this.mpBlogUserService.getMPBlogUsers());
        return "redirect:/listUser";
    }

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
