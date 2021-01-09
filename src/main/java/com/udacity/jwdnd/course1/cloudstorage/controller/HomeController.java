package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.dto.NoteDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller()
@RequestMapping("/home")
public class HomeController {
    //Auto injection
    @Autowired
    private CredentialsService credentialsService;

    //Fields including the service
    private final FileStorageService fileStorageService;
    private final UserService userService;
    private final NotesService notesService;
    //Constructor


    public HomeController(FileStorageService fileStorageService, UserService userService,
                          NotesService notesService) {
        this.fileStorageService = fileStorageService;
        this.userService = userService;
        this.notesService = notesService;
    }

    @ModelAttribute("fileDTO")
    public FileDTO getFileDTO() {
        return new FileDTO();
    }

    @ModelAttribute("noteDTO")
    public NoteDTO getNoteDTO() {return new NoteDTO(); }

    @GetMapping()
    public String viewHome(Model model, Principal principal) throws Exception {
        User user = this.userService.getUser(principal.getName());
        model.addAttribute("files", fileStorageService.getAllByUserId(user.getUserId()));
        model.addAttribute("notes", notesService.getAllNote(user.getUserId()));
        model.addAttribute("credentials", credentialsService.getAllCredentials(user.getUserId()));
        return "home";
    }



}
