package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private final Logger logger = LoggerFactory.getLogger(CredentialController.class);

    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }
    @PostMapping
    public String createOrUpdateCredential(@ModelAttribute Credentials credential,
                                           Authentication authentication,
                                           RedirectAttributes redirectAttributes){

        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);

        if (credential.getCredentialId() >0){
            try {
                credentialService.update(credential);
                redirectAttributes.addFlashAttribute("successMessage", "Credentials were successfully updated");
                return "redirect:/result";

            }catch (Exception e){
                logger.error(e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Credentials update failed. Please try again");
                return "redirect:/result";
            }

        }else {
            try {
                credentialService.create(credential);
                redirectAttributes.addFlashAttribute("successMessage", "Credentials were successfully created");
                return "redirect:/result";

            }catch (Exception e){
                logger.error(e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Credentials creation failed. Please try again");
                return "redirect:/result";
            }
        }

    }

    @RequestMapping("/{credentialId}/delete")
    public String deleteCredential(@PathVariable Integer credentialId, RedirectAttributes redirectAttributes){

        try {
            credentialService.deleteCredential(credentialId);
            redirectAttributes.addFlashAttribute("successMessage", "Credentials were successfully deleted");
            return "redirect:/result";

        }catch (Exception e){
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Credentials delete failed. Please try again");
            return "redirect:/result";
        }


    }



}
