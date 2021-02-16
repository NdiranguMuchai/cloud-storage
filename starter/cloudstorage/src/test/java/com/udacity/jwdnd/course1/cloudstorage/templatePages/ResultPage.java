package com.udacity.jwdnd.course1.cloudstorage.templatePages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {
    @FindBy(tagName = "a")
    private WebElement homeLink;
    private final WebDriver webDriver;

    public ResultPage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickLink(){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.homeLink);
    }
}
