package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesStorageService;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller()
@RequestMapping("/home")
public class HomeController {

    private final UserMapper userMapper;
    private final FileStorageService fileStorageService;
    private final CredentialsStorageService credentialsStorageService;
    private final NotesStorageService notesStorageService;

    @Autowired
    public HomeController(FileStorageService fileStorageService, CredentialsStorageService credentialsStorageService,
                          NotesStorageService notesStorageService, UserMapper userMapper) {
        this.fileStorageService = fileStorageService;
        this.credentialsStorageService = credentialsStorageService;
        this.notesStorageService = notesStorageService;
        this.userMapper = userMapper;
    }

    @GetMapping()
    public String viewHome(Model model, Principal principal) {
        User user = userMapper.getUser(principal.getName());
        model.addAttribute("files", fileStorageService.getAllByUserId(user.getUserId()));
        return "home";
    }



}
