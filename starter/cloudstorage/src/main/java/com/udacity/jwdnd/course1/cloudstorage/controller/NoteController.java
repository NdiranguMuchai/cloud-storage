package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String createOrUpdateNote(@ModelAttribute Note note, Authentication authentication, Model model){
        User user = userService.getUser(authentication.getName());

        Integer userId = user.getUserId();
        note.setUserId(userId);

       if (note.getNoteId() > 0){
           noteService.update(note);
       }else {
           noteService.createNote(note);
       }

        return "redirect:/home";
    }

    @RequestMapping("/{noteId}/delete")
    public String deleteNote(@PathVariable Integer noteId){
         noteService.deleteNote(noteId);
        return "redirect:/home";
    }
}
