package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

@Service
public class UserActionMessages {
    /**
     * Files
     */
    public final String FILE_UPLOAD_SUCCESS = "File was successfully uploaded.";
    public final String FILE_UPLOAD_ERROR = "File upload failed. Please try again.";

    public final String FILE_DELETE_SUCCESS = "File was successfully deleted.";
    public final String FILE_DELETE_ERROR = "File item delete failed. Please try again.";

    public final String FILE_NAME_EXISTS = "Sorry, you cannot upload two files with the same name.";

    public final String NO_FILE_SELECTED = "Please select a file to upload.";

    /**
     * Notes
     */
    public final String NOTE_CREATE_SUCCESS = "Note was successfully created.";
    public final String NOTE_CREATE_ERROR = "Note item creation failed. Please try again.";

    public final String NOTE_UPDATE_SUCCESS = "Note was successfully updated.";
    public final String NOTE_UPDATE_ERROR = "Note item update failed. Please try again.";

    public final String NOTE_DELETE_SUCCESS = "Note was successfully deleted.";
    public final String NOTE_DELETE_ERROR = "Note item delete failed. Please try again.";

    public final String NOTE_ALREADY_EXISTS = "Sorry note already exists. Please try again.";

    /**
     *Credentials
     */
    public final String CREDENTIALS_CREATE_SUCCESS = "Credentials successfully created.";
    public final String CREDENTIALS_CREATE_ERROR = "Credentials creation failed. Please try again.";

    public final String CREDENTIALS_UPDATE_SUCCESS = "Credentials were successfully updated.";
    public final String CREDENTIALS_UPDATE_ERROR = "Credentials update failed. Please try again.";

    public final String CREDENTIALS_DELETE_SUCCESS = "Credentials were successfully deleted.";
    public final String CREDENTIALS_DELETE_ERROR = "Credentials delete failed. Please try again.";




}
