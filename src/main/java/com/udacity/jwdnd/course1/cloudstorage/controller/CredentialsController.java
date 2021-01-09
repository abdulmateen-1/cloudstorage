package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import javax.annotation.PostConstruct;

@Controller
public class CredentialsController {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void postConstruct() {
        System.out.println("creating a credential controller");
    }

    @ModelAttribute
    public CredentialDTO getCredentialDTO() {
        return new CredentialDTO();
    }

    @PostMapping("/home/credential/newCredential")
    public String postCredential(@ModelAttribute("credentialDTO") CredentialDTO credential, Model model, Authentication auth) {
        //Adding a new credential
        String message;
        if (credential.getCredentialId() == null) {
            credentialsService.addCredential(credential, auth);
            model.addAttribute("updateSuccess", true);
        } else {
            credentialsService.updateCredential(credential);
            message = "Credential updated successfully";
            model.addAttribute("updateSuccess", message);
        }
        return "result";
    }

    @GetMapping
    public String getCredentialPage(@ModelAttribute("credentialDTO") CredentialDTO credential, Model model, Authentication auth) {
        String username = auth.getName();
        User user = userService.getUser(username);
        int userId = user.getUserId();

        model.addAttribute("credential.username", username);
        model.addAttribute("credential.password", credential.getPassword());

        return "home";
    }

    @GetMapping("/home/credential/delete/{credentialId}")
    public String deleteCredential(@PathVariable int credentialId, Model model) {
        int currentCredentialId = credentialsService.deleteCredential(credentialId);
        String message = null;

        if (credentialId < 1)
            message = "There was an error deleting the credentials";

        if (message == null)
            model.addAttribute("updateSuccess", true);
        else
            model.addAttribute("updateFailed", message);
        return "result";
    }


}
