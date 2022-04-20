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

/**
 * Controller class for managing the entries
 * Uses entry and session services
 */
@Controller
public class MPBlogEntryController {
    private final MPBlogEntryService mpBlogEntryService;
    private final MPBlogSessionService mpBlogSessionService;

    public MPBlogEntryController(MPBlogEntryService mpBlogEntryService,
                                 MPBlogSessionService mpBlogSessionService) {

        this.mpBlogEntryService = mpBlogEntryService;
        this.mpBlogSessionService = mpBlogSessionService;
    }

    /**
     * GetMapping method to call the html data that is responsible for creating a new entry
     *
     *@param model used to transfer data from model to view through the controller
     *
     * @return directory pad of the regarding html data
     */
    @GetMapping("/newEntry")
    public String newEntry(Model model) {

        MPBlogEntry mpBlogEntry = new MPBlogEntry();

        model.addAttribute("entry", mpBlogEntry);

        return "new/newEntry";
    }

    /**
     * Postmapping method to check the user input through the entry creation template
     * Checks the user input and creates a new entry when the conditions are met
     *
     * @param sessionId id of the current session
     * @param bindingResult set of rules that the data should follow
     * @return to the entry form, when the conditions are not met, to the list of entries when the entry is
     * successfully created
     */
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

        // Attach a standard image to the entry
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

    /**
     * Getmapping method to change the image belonging to an entry
     *
     * @param id of the current entry
     * @param model used to transfer data from model to view through the controller
     * @return to the regarding html data when the conditions are met, if not back to the entry detail page
     */

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

    /**
     * Postmapping method to save the user chosen image to an entry
     *
     * @param id of the current entry
     * @param picture image data uploaded by the user
     * @return to the entry details page after saving the image
     */
    @PostMapping("/{id}/updatePicture")
    public String updatePictureResult(@PathVariable int id,
                                      @RequestParam("picture") MultipartFile picture) {

        this.mpBlogEntryService.
                getMPBlogEntry(id).
                ifPresent(mpBlogEntry -> mpBlogEntry.setPicture(picture));

        return "redirect:/" + id + "/showEntry";
    }

    /**
     * Getmapping method that addresses the homepage/an overlook to the blog entries.
     * @param model used to transfer data from model to view through the controller
     * @return the data containing the information to the homepage with the list of entries, latest shown first.
     */
    @GetMapping({"/listEntries", "/", "/listentries"})
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

    /**
     * Getmapping method to show the details of a certain entry (full text, user details and comments)
     *
     * @param id of the current post
     * @param model used to transfer data from model to view through the controller
     * @return to the entry page when the requested entry exists, to the list of entries if the entry
     * does not exist anymore
     */
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

    /**
     * Getmapping method to delete a certain entry
     *
     * @param sessionId id of the current session
     * @param id of the entry to be deleted
     * @return after checking a set of conditions like authorization, deletes the entry
     * If the conditions are not met, throws an exception and aborts the mission
     */
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

    /**
     * Getmapping method to edit a certain entry
     *
     * @param model used to transfer data from model to view through the controller
     * @param id of the entry to be edited
     * @return to the html data already storing the information to be edited (Entry title and content)
     */

    @GetMapping("/{id}/updateEntry")
    public String updateEntry(Model model,
                              @PathVariable int id) {

        model.addAttribute("entry", this.mpBlogEntryService.findById(id));
        return "update/updateEntry";
    }

    /**
     * Postmapping method to update a certain entry using the data provided by the user
     * through the related Getmapping method
     *
     * @param mpBlogEntry the entry to be edited, that is already stored as a model attribute
     * @param id of the entry to be updated
     * @return to the list view of the entries after saving the changes
     */
    @PostMapping("/{id}/updateEntry")
    public String updateEntry(@ModelAttribute("MPBlogEntry") MPBlogEntry mpBlogEntry,
                              @PathVariable int id) {

        this.mpBlogEntryService.updateTitle(id, mpBlogEntry.getTitle());
        this.mpBlogEntryService.updateContent(id, mpBlogEntry.getContent());
        return "redirect:/listEntries";
    }
}
