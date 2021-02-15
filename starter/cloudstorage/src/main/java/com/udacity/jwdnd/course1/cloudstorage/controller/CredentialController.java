package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }
    @PostMapping
    public String createCredential(@ModelAttribute Credentials credential, Authentication authentication, Model model){
        String credentialAddedError = null;

        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);

        int rowsAdded = credentialService.create(credential);
        if (rowsAdded < 0){
            credentialAddedError = "Error while creating credential. Please try again";
        }
        if (credentialAddedError == null) {
            model.addAttribute("credentialAddedSuccess", true);
        } else {
            model.addAttribute("credentialAddedError", credentialAddedError);
        }


        return "redirect:/home";
    }

}
