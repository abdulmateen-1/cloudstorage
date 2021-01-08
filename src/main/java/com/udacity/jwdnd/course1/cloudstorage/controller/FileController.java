package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileStorageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
public class FileController {
    private final FileStorageService fileStorageService;

    private final UserMapper userMapper;

    public FileController(FileStorageService fileStorageService, UserMapper userMapper) {
        this.fileStorageService = fileStorageService;
        this.userMapper = userMapper;
    }

    /*
     * The Get method from the http Request allows modification to be made
     * to the database. this reduce what we have to do in our post request*/
    @GetMapping("/home/file/view/{fileId}")
    public ResponseEntity<ByteArrayResource> viewFile(Model model, @PathVariable("fileId") int fileId) {
        File file = fileStorageService.getById(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline: filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/home/file/delete/{fileId}")
    public String deleteFile(Model model, @PathVariable("fileId") int fileId) {
        String deleteMessage = null;

        int deletedRow = fileStorageService.delete(fileId);

        if (deletedRow < 0)
            deleteMessage = "Error occurred during deletion, try again.";

        if (deleteMessage == null)
            model.addAttribute("updateSuccess", true);
        else
            model.addAttribute("updateFailed", deleteMessage);

        return "result";
    }

    @ModelAttribute("fileDTO")
    public FileDTO getFileDTO() {
        return new FileDTO();
    }

    @PostMapping("/home/file/newFile")
    public String uploadFile(@ModelAttribute("fileDTO") MultipartFile file, Model model, Authentication auth) throws IOException {
        String message = null;

        User user = userMapper.getUser(auth.getName());
        int currentUserId = user.getUserId();

        //Edge Cases
        if (file.isEmpty())
            message = "File should not be empty";

        if (fileStorageService.isFileNameAvailable(file.getOriginalFilename())) {
            //Upload files to Files db by fileId;
            //return fileId if success
            int fileId = fileStorageService.store(file, currentUserId);
            if (fileId < 0)
                message = "There was an error uploading this file";
        } else
            message = "A file with that name already exists, please change the name and try again.";


        //Display success/failure message on result.html
        if (message == null)
            model.addAttribute("updateSuccess", true);
        else
            model.addAttribute("updateFailed", message);
        return "result";
    }
}
