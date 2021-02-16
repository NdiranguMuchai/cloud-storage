package com.udacity.jwdnd.course1.cloudstorage.userActions;

import com.udacity.jwdnd.course1.cloudstorage.templatePages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.templatePages.NotesSection;
import com.udacity.jwdnd.course1.cloudstorage.templatePages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.templatePages.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteActions {
    @LocalServerPort
    private int port;

    private WebDriver driver;
    public String BASE_URL;
    public static final String NOTE_INFO= "Maneno";


    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        BASE_URL = "http://localhost:" + port;

        //login
        String username = "uleMsee";
        String password = "kipassword";

        driver.get(BASE_URL + "/signup");
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signup("Robert", "Lewandowski", username, password);


        driver.get(BASE_URL +"/login");
        LoginPage loginPage = new LoginPage(driver);
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

}
