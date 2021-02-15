package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final NoteService noteService;
    private final UserService userService;
    private final CredentialService credentialService;

    public HomeController(NoteService noteService, UserService userService, CredentialService credentialService){
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService  =credentialService;
    }

    @GetMapping
    public String getHomePage(Model model, Authentication authentication){
        User user = userService.getUser(authentication.getName());

        model.addAttribute("notes",noteService.getNotes(user.getUserId()));
        model.addAttribute("credentialsList", credentialService.listAllCredentials(user.getUserId()));

        return "home";
    }

}
