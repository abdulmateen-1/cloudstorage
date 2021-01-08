package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(className = "logout")
    private WebElement logoutLink;

    @FindBy(id = "nav-files-tab")
    private WebElement filesTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void gotoLogout() {
        logoutLink.click();
    }

    public void gotoFileTab() {
        filesTab.click();
    }

    public void gotoNoteTab() {
        notesTab.click();
    }

    public void gotoCredentialsTab() {
        credentialsTab.click();
    }
}
