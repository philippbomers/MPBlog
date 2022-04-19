package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.services.MPBlogEntryService;
import com.example.mpblog.services.MPBlogSessionService;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

@Controller
public class MPBlogEntryController {
    private final MPBlogEntryService mpBlogEntryService;
    private final MPBlogSessionService mpBlogSessionService;

    public MPBlogEntryController(MPBlogEntryService mpBlogEntryService,
                                 MPBlogSessionService mpBlogSessionService) {

        this.mpBlogEntryService = mpBlogEntryService;
        this.mpBlogSessionService = mpBlogSessionService;
    }

    @GetMapping("/newEntry")
    public String newEntry(Model model) {

        MPBlogEntry mpBlogEntry = new MPBlogEntry();

        model.addAttribute("entry", mpBlogEntry);

        return "new/newEntry";
    }

    @PostMapping("/newEntry")
    public String newEntrySubmit(@CookieValue(name = "sessionId") String sessionId,
                                 @Valid @ModelAttribute("entry") MPBlogEntry entry,
                                 BindingResult bindingResult) {

        Optional<MPBlogUser> mpBlogUser = this.mpBlogSessionService.findMPBlogUserById(sessionId);

        if (bindingResult.hasErrors() ||
                mpBlogUser.isEmpty()) {
            return "new/newEntry";
        }

        MPBlogEntry newEntry = new MPBlogEntry();
        newEntry.setTitle(entry.getTitle());
        newEntry.setContent(entry.getContent());
        newEntry.setMpBlogUser(mpBlogUser.get());
        this.mpBlogEntryService.save(newEntry);

        try {
            FileInputStream input = new FileInputStream("src/main/java/com/example/mpblog/images/standardpicture.jpeg");

            MultipartFile multipartFile = new MockMultipartFile(
                    "fileItem",
                    "entry",
                    "image/jpeg",
                    input);

            this.mpBlogEntryService.
                    getMPBlogEntry().
                    stream().
                    distinct().
                    findFirst().
                    ifPresent(mpBlogEntry -> mpBlogEntry.
                            setPicture(multipartFile));

        } catch (IOException ioException) {
            return "redirect:/listEntries";
        }
        return "redirect:/listEntries";
    }

    @GetMapping("/{id}/updatePicture")
    public String updatePicture(@PathVariable int id,
                                Model model) {

        Optional<MPBlogEntry> entry = this.mpBlogEntryService.getMPBlogEntry(id);

        if (entry.isPresent()) {
            model.addAttribute("entry", entry.get());
            return "update/updatePicture";
        }
        return "redirect:/" + id + "/showEntry";
    }

    @PostMapping("/{id}/updatePicture")
    public String updatePictureResult(@PathVariable int id,
                                      @RequestParam("picture") MultipartFile picture) {

        this.mpBlogEntryService.
                getMPBlogEntry(id).
                ifPresent(mpBlogEntry -> mpBlogEntry.setPicture(picture));

        return "redirect:/" + id + "/showEntry";
    }

    @GetMapping({"/listEntries", "/"})
    public String listEntries(Model model) {

        model.addAttribute("entries",
                this.mpBlogEntryService
                        .getMPBlogEntry());

        model.addAttribute("shortEntries",
                mpBlogEntryService
                        .mapTheShortContent(this.mpBlogEntryService.
                                findAll()));

        return "list/listEntries";
    }

    @GetMapping("/{id}/showEntry")
    public String showEntry(@PathVariable int id,
                            Model model) {

        Optional<MPBlogEntry> entry = this.mpBlogEntryService.getMPBlogEntry(id);

        if (entry.isPresent()) {
            model.addAttribute("entry", entry.get());
            return "show/showEntry";
        }
        return "redirect:/showEntry";
    }

    @GetMapping("/{id}/deleteEntry")
    public String deleteEntry(@CookieValue(value = "sessionId", defaultValue = "") String sessionId,
                              @PathVariable int id) {

        MPBlogEntry entry = this.mpBlogEntryService.findById(id);
        Optional<MPBlogSession> optionalSession = this.mpBlogSessionService.findById(sessionId);

        if (optionalSession.isPresent() &&
                (entry.getMpBlogUser() == optionalSession.get().getMpBlogUser() ||
                        optionalSession.get().getMpBlogUser().isAdminRights())) {

            this.mpBlogEntryService.delete(entry);
            return "redirect:/listEntries";
        }
        throw new IllegalArgumentException("User is not authorized to delete this entry!");
    }

    @GetMapping("/{id}/updateEntry")
    public String updateEntry(Model model,
                              @PathVariable int id) {

        model.addAttribute("entry", this.mpBlogEntryService.findById(id));
        return "update/updateEntry";
    }

    @PostMapping("/{id}/updateEntry")
    public String updateEntry(@ModelAttribute("MPBlogEntry") MPBlogEntry mpBlogEntry,
                              @PathVariable int id) {

        this.mpBlogEntryService.updateTitle(id, mpBlogEntry.getTitle());
        this.mpBlogEntryService.updateContent(id, mpBlogEntry.getContent());
        return "redirect:/listEntries";
    }
}
