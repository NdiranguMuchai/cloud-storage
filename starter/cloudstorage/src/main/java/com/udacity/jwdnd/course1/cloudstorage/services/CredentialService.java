package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.security.HashService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final HashService hashService;

    public CredentialService(CredentialMapper credentialMapper, HashService hashService) {
        this.credentialMapper = credentialMapper;
        this.hashService = hashService;
    }

    public List<Credentials> listAllCredentials(Integer userId){
        return credentialMapper.listAllCredentials(userId);
    }

    public int create(Credentials credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String hashedPassword = hashService.getHashedValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(hashedPassword);
        return credentialMapper.create(credential);
    }

    public Credentials findOne(Integer credentialId){
        return credentialMapper.findOne(credentialId);
    }

    public void deleteCredential(Integer credentialId){
        credentialMapper.delete(credentialId);
    }
}
