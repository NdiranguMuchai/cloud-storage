package com.udacity.jwdnd.course1.cloudstorage.userActions;

import com.udacity.jwdnd.course1.cloudstorage.templatePages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteActions {
    @LocalServerPort
    private int port;

    private WebDriver driver;
    public String BASE_URL;
    public static final String NOTE_INFO= "Maneno";
    public static final String NOTE_INFO_EDIT= "x";

    private final String username = "uleMsee";
    private final String password = "kipassword";
    private LoginPage loginPage;


    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        BASE_URL = "http://localhost:" + port;

        //login

        driver.get(BASE_URL + "/signup");
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signup("Robert", "Lewandowski", username, password);


        driver.get(BASE_URL +"/login");
        loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        driver.get(BASE_URL +"/home");
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    void addNote(){
        NotesSection notesSection = new NotesSection(driver);
        notesSection.createNote(NOTE_INFO, NOTE_INFO);

        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickLink();
        assertEquals("Home", driver.getTitle());

        notesSection.clickNoteBar();
        assertEquals(NOTE_INFO, notesSection.getNoteTitleText());
    }


    @Test
    void editNote(){
        //create note
        NotesSection notesSection = new NotesSection(driver);
        notesSection.createNote(NOTE_INFO, NOTE_INFO);
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickLink();

        //logout
        HomePage homePage = new HomePage(driver);
        homePage.logout();
        driver.get(BASE_URL +"/login");
        assertEquals("Login", driver.getTitle());

        //login
        loginPage.login(username, password);
        driver.get(BASE_URL +"/home");
        assertEquals("Home", driver.getTitle());

        //edit note
        notesSection.editNote(NOTE_INFO_EDIT, NOTE_INFO_EDIT);
        resultPage.clickLink();
        notesSection.clickNoteBar();
        assertEquals(NOTE_INFO_EDIT, notesSection.getNoteTitleText());
    }

    @Test
    void  deleteNote(){
        //create note
        NotesSection notesSection = new NotesSection(driver);
        notesSection.createNote(NOTE_INFO, NOTE_INFO);
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickLink();

        //logout
        HomePage homePage = new HomePage(driver);
        homePage.logout();
        driver.get(BASE_URL +"/login");
        assertEquals("Login", driver.getTitle());

        //login
        loginPage.login(username, password);
        driver.get(BASE_URL +"/home");
        assertEquals("Home", driver.getTitle());

        //delete
        notesSection.deleteNote();
        resultPage.clickLink();
        notesSection.clickNoteBar();
        assertThrows(NoSuchElementException.class, notesSection::getNoteTitleText);
    }

}
