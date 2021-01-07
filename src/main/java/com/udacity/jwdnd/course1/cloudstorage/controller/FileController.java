package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;


@Controller
public class FileController {
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserMapper userMapper;

    /*
     * The Get method from the http Request allows modification to be made
     * to the database. this reduce what we have to do in our post request*/
    @GetMapping("/view/file/{fileId}")
    public ResponseEntity<ByteArrayResource> viewFile(Model model, @PathVariable("fileId") int fileId) {
        File file = fileStorageService.getById(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline: filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/delete/file/{fileId}")
    public String deleteFile(Model model, @PathVariable("fileId") int fileId) {
        String deleteMessage = null;

        int deletedRow = fileStorageService.delete(fileId);

        if (deletedRow < 0)
            deleteMessage = "Error occurred during deletion, try again.";

        return "result";
    }
    @ModelAttribute("fileDTO")
    public FileDTO getFileDTO(){return new FileDTO();}

    @PostMapping("/upload/file")
    public String uploadFile(@ModelAttribute("fileDTO") MultipartFile multipartFile, Model model, Authentication auth) {
        String message = null;

        User user = userMapper.getUser(auth.getName());
        //Edge Cases
        if (multipartFile.isEmpty())
            message = "File should not be empty";

        if (fileStorageService.isFileNameAvailable(multipartFile.getOriginalFilename())) {
            int rowsAdded = fileStorageService.store(multipartFile, user.getUserId());
            if (rowsAdded <= 0)
                message = "There was an error during the upload, please try again";
        } else {
            message = "A file with that name already exists, please change the name and try again later";
        }

        //Display success/failure message on result.html
        if (message == null)
            model.addAttribute("updateSuccess", true);
        else
            model.addAttribute("updateFailed", message);
        return "result";
    }
}
