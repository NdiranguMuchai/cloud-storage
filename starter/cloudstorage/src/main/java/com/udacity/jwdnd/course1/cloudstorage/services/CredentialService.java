package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.security.EncryptionService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper,
                             EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credentials> listAllCredentials(Integer userId){
        return credentialMapper.listAllCredentials(userId);
    }

    public Credentials findOne(Integer credentialId){
        return credentialMapper.findOne(credentialId);
    }


    private void encryptPassword(Credentials credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
    }

    public int create(Credentials credential){
        encryptPassword(credential);
        return credentialMapper.create(credential);
    }

    public void update(Credentials credential){
        encryptPassword(credential);
        credentialMapper.update(credential);
    }

    public void deleteCredential(Integer credentialId){
        credentialMapper.delete(credentialId);
    }


}
