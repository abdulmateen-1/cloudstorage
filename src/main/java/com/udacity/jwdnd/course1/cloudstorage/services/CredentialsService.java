package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialDTO;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {

    //Trying out auto injection of the Spring beans
    @Autowired
    private UserService userService;

    @Autowired
    private CredentialsMapper credentialsMapper;

    @Autowired
    private EncryptionService encryptionService;

    public List<Credential> getAllCredentials(Integer userId) throws Exception {
        List<Credential> credentials = credentialsMapper.getAllCredentials(userId);
        if (credentials == null)
            throw new Exception();
        return credentials;
    }

    public Credential getCredentialByFileId(Integer fileId) {
        return credentialsMapper.getCredentialByFileId(fileId);
    }

    public void addCredential(CredentialDTO credential, Authentication auth) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        //Add in new Credential
        User user = userService.getUser(auth.getName());
        int userId = user.getUserId();
        credentialsMapper.addCredential(new Credential(null,
                credential.getUrl(),
                credential.getUsername(),
                encodedKey,
                encryptedPassword,
                userId));
    }

    public void updateCredential(CredentialDTO credentialDTO) {
        Credential credential = credentialsMapper.getCredentialByFileId(credentialDTO.getCredentialId());
        String encryptedPassword = encryptionService.encryptValue(credentialDTO.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);
        credential.setUrl(credentialDTO.getUrl());
        credential.setUsername(credentialDTO.getUsername());
        credentialsMapper.updateCredentials(credential);

    }

    public int deleteCredential(Integer credentialId) {
        return credentialsMapper.deleteCredential(credentialId);
    }
}
