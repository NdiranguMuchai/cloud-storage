package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserActionMessages;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/notes")
public class NoteController {
    private final Logger logger = LoggerFactory.getLogger(NoteController.class);

    private final NoteService noteService;
    private final UserService userService;
    private final UserActionMessages userActionMessages;

    public NoteController(NoteService noteService,
                          UserService userService,
                          UserActionMessages userActionMessages) {

        this.noteService = noteService;
        this.userService = userService;
        this.userActionMessages = userActionMessages;
    }

    @PostMapping
    public String createOrUpdateNote(@ModelAttribute Note note,
                                     Authentication authentication,
                                     RedirectAttributes redirectAttributes){

        User user = userService.getUser(authentication.getName());

        Integer userId = user.getUserId();
        note.setUserId(userId);

        if (noteService.findByTitleAndDesc(note.getNoteTitle(), note.getNoteDescription()) != null){
            redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.NOTE_ALREADY_EXISTS);
        }else

        if (note.getNoteId() > 0){

            try {
                noteService.update(note);

                redirectAttributes.addFlashAttribute("successMessage", userActionMessages.NOTE_UPDATE_SUCCESS);
                return "redirect:/result";

            }catch (Exception e){
                logger.error(e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.NOTE_UPDATE_ERROR);
                return "redirect:/result";
            }

        }else {

            try {
                noteService.createNote(note);

                redirectAttributes.addFlashAttribute("successMessage", userActionMessages.NOTE_CREATE_SUCCESS);
                return "redirect:/result";

            }catch (Exception e){
                logger.error(e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.NOTE_CREATE_ERROR);
                return "redirect:/result";
            }

        }

        return "redirect:/result";
    }

    @RequestMapping("/{noteId}/delete")
    public String deleteNote(@PathVariable Integer noteId, RedirectAttributes redirectAttributes){
        try {

            noteService.deleteNote(noteId);

            redirectAttributes.addFlashAttribute("successMessage", userActionMessages.NOTE_DELETE_SUCCESS);
            return "redirect:/result";

        }catch (Exception e){
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.NOTE_DELETE_ERROR);
            return "redirect:/result";
        }

    }
}
