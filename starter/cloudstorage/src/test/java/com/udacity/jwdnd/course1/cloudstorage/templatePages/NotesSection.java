package com.udacity.jwdnd.course1.cloudstorage.templatePages;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotesSection {
    @FindBy(id="nav-notes-tab")
    private WebElement noteBar;


    @FindBy(id="addNotesButton")
    private WebElement addNotesButton;

    @FindBy(id="note-title")
    private WebElement noteTitleField;

    @FindBy(id="note-description")
    private WebElement noteDescriptionField;

    @FindBy(id="submitNoteButton")
    private WebElement submitNoteButton;

    @FindBy(id="noteTitleDisplay")
    private WebElement noteTitleText;

    private final  WebDriver webDriver;

    public  NotesSection(WebDriver webDriver){
        this.webDriver =webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickNoteBar(){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.noteBar);
    }

    public void createNote(String noteTitle, String noteDescription){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.noteBar);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.addNotesButton);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + noteTitle + "';", this.noteTitleField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + noteDescription + "';", this.noteDescriptionField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.submitNoteButton);
    }

    public String getNoteTitleText() {
        return noteTitleText.getAttribute("innerHTML");
    }

}
