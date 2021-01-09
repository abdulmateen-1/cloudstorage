package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class NotePage {

    @FindBy(id = "nav-notes-tab")
    private WebElement navNoteTab;

    @FindBy(id = "add-note")
    private WebElement addNote;

    @FindBy(id = "edit-note")
    private List<WebElement> editNote;

    @FindBy(id = "delete-note")
    private List<WebElement> deleteNote;

    @FindBy(id = "notetitle")
    private List<WebElement> titleList;

    @FindBy(id = "notedescription")
    private List<WebElement> descriptionList;

    @FindBy(id = "note-title")
    private WebElement inputTitle;

    @FindBy(id = "note-description")
    private WebElement inputDescription;

    @FindBy(id = "noteSubmit")
    private WebElement submitButton;

    @FindBy(id = "note-modal")
    private WebElement submitModal;

    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public List<String> getDetail(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("StackTrace Message: " + e.getMessage());
        }

        wait.until(ExpectedConditions.visibilityOf(navNoteTab)).click();
        navNoteTab.click();
        wait.until(ExpectedConditions.visibilityOf(addNote));

        return new ArrayList<>(List.of(titleList.get(0).getText(),
                descriptionList.get(0).getText()));
    }

    public void addNote(WebDriver driver, String title, String description, WebElement nav) {
        WebDriverWait wait = new WebDriverWait(driver, 15);

        try {
            wait.until(ExpectedConditions.visibilityOf(navNoteTab)).click();
        } catch (TimeoutException e) {
            System.out.println("Timeout Exception");
            nav.click();
            wait.until(ExpectedConditions.visibilityOf(navNoteTab)).click();
        }

        wait.until(ExpectedConditions.visibilityOf(addNote)).click();
        wait.until(ExpectedConditions.visibilityOf(inputTitle)).sendKeys(title);

        wait.until(ExpectedConditions.visibilityOf(inputDescription)).sendKeys(description);
        wait.until(ExpectedConditions.visibilityOf(submitModal)).click();
//        wait.until(ExpectedConditions.visibilityOf(navNoteTab)).click();
    }

    public void editNote(WebDriver driver, String title, String description) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOf(navNoteTab)).click();
        wait.until(ExpectedConditions.visibilityOf(editNote.get(0))).click();
        wait.until(ExpectedConditions.visibilityOf(inputTitle));

        inputTitle.clear();
        inputTitle.sendKeys(title);

        wait.until(ExpectedConditions.visibilityOf(inputDescription));
        inputDescription.clear();
        inputDescription.sendKeys(description);

        wait.until(ExpectedConditions.visibilityOf(submitModal)).click();
    }

    public void deleteNote(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(navNoteTab)).click();
        wait.until(ExpectedConditions.visibilityOf(deleteNote.get(0))).click();
    }
}
