package com.example.mpblog.controllers;

import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.entities.MPBlogSession;
import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.services.MPBlogEntryService;
import com.example.mpblog.services.MPBlogSessionService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.util.Optional;

@Controller
public class MPBlogEntryController {
    private final MPBlogEntryService mpBlogEntryService;
    private final MPBlogSessionService mpBlogSessionService;

    public MPBlogEntryController(MPBlogEntryService mpBlogEntryService, MPBlogSessionService mpBlogSessionService) {
        this.mpBlogEntryService = mpBlogEntryService;
        this.mpBlogSessionService = mpBlogSessionService;
    }

    @GetMapping("/newEntry")
    public String entry(Model model) {
        model.addAttribute("entry", new MPBlogEntry());
        return "new/newentry";
    }

    @GetMapping("/{id}/updatepicture")
    public String updatepicture(@PathVariable int id, Model model) {
        model.addAttribute("entry", this.mpBlogEntryService.getMPBlogEntry(id).get());
        return "update/updatepicture";
    }

    @PostMapping("/{id}/updatepicture")
    public String updatepictureresult(@PathVariable int id, @RequestParam("picture") MultipartFile picture) {
        this.mpBlogEntryService.getMPBlogEntry(id).ifPresent(mpBlogEntry -> mpBlogEntry.setPicture(picture));
        return "redirect:/"+id+"/showentry";
    }

    @PostMapping("/newEntry")
    public String message(@CookieValue(name = "sessionId") String sessionId, @Valid @ModelAttribute("entry") MPBlogEntry entry, BindingResult bindingResult) {
        Optional<MPBlogUser> mpBlogUser = this.mpBlogSessionService.findMPBlogUserById(sessionId);
        if (bindingResult.hasErrors() || mpBlogUser.isEmpty()) {
            return "new/newentry";
        }
        MPBlogEntry newEntry = new MPBlogEntry();
        newEntry.setTitle(entry.getTitle());
        newEntry.setContent(entry.getContent());
        newEntry.setMpBlogUser(mpBlogUser.get());
        this.mpBlogEntryService.save(newEntry);

        try{
            FileInputStream input = new FileInputStream("src/main/java/com/example/mpblog/images/standardpicture.jpeg");
            MultipartFile multipartFile = new MockMultipartFile("fileItem",
                    "entry", "image/png", input);
            Optional<MPBlogEntry> currentEntry = this.mpBlogEntryService.getMPBlogEntry().stream().distinct().findFirst();
            currentEntry.ifPresent(mpBlogEntry -> mpBlogEntry.setPicture(multipartFile));
        }catch (IOException ioException){
            return "redirect:/listentries";
        }

        return "redirect:/listentries";
    }

    @GetMapping({"/listentries", "/"})
    public String showEntries(Model model) {
        model.addAttribute("entries", this.mpBlogEntryService.getMPBlogEntry());
        model.addAttribute("shortEntries", mpBlogEntryService.mapTheShortContent(this.mpBlogEntryService.findAll()));
        return "list/listentries";
    }

    @GetMapping("/{id}/showentry")
    public String entryDetails(@PathVariable int id, Model model) {
        Optional<MPBlogEntry> entry = this.mpBlogEntryService.getMPBlogEntry(id);
        if (entry.isPresent()) {
            model.addAttribute("entry", entry.get());
            return "show/showentry";
        }
        return "redirect:/showentry";
    }

    @GetMapping("/{id}/deleteEntry")
    public String delete(@CookieValue(value = "sessionId", defaultValue = "") String sessionId, @PathVariable int id) {
        MPBlogEntry entry = this.mpBlogEntryService.findById(id);
        Optional<MPBlogSession> optionalSession = this.mpBlogSessionService.findById(sessionId);
        if (optionalSession.isPresent() &&
                (entry.getMpBlogUser() == optionalSession.get().getMpBlogUser() ||
                        optionalSession.get().getMpBlogUser().isAdminRights())) {
            this.mpBlogEntryService.delete(entry);
            return "redirect:/listentries";
        }
        throw new IllegalArgumentException("User is not authorized to delete this entry!");
    }

    @GetMapping("/{id}/editEntry")
    public String editEntryForm(Model model, @PathVariable int id) {
        model.addAttribute("entry", this.mpBlogSessionService.findById(String.valueOf(id)));
        return "update/updateentry";
    }

    @PostMapping("/{id}/editEntry")
    public String updateEntry(@ModelAttribute("MPBlogEntry") MPBlogEntry mpBlogEntry, @PathVariable int id) {
        //update entry in the database
        this.mpBlogEntryService.updateTitle(id, mpBlogEntry.getTitle());
        this.mpBlogEntryService.updateContent(id, mpBlogEntry.getContent());
        return "redirect:/listentries";
    }
    /*@PostMapping("/{id}/editEntry")
    public String editEntry(@CookieValue(value = "sessionId", defaultValue = "") String sessionId, @PathVariable int id) {
        MPBlogEntry entry = mpBlogEntryRepository.findById(id);
        Optional<MPBlogSession> optionalSession = this.mpBlogSessionService.findById(sessionId);
        if (optionalSession.isPresent() &&
                (entry.getMpBlogUser() == optionalSession.get().getMpBlogUser() ||
                        optionalSession.get().getMpBlogUser().isAdminRights())) {
            mpBlogEntryRepository.delete(entry);
            return "redirect:/showentries";
        }
        throw new IllegalArgumentException("User is not authorized to delete this entry!");
    }*/
}
