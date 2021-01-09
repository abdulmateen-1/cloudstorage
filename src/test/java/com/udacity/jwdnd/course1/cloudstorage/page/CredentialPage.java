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

public class CredentialPage {
    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "add-credentials")
    private WebElement addCredentials;

    @FindBy(id = "edit-credential")
    private WebElement editCredential;

    @FindBy(id = "delete-credential")
    private WebElement deleteCredential;

    @FindBy(id = "url")
    private List<WebElement> credentialUrlList;

    @FindBy(id = "username")
    private List<WebElement> credentialUsernameList;

    @FindBy(id = "password")
    private List<WebElement> credentialPasswordList;

    @FindBy(id = "credentialSubmit")
    private WebElement saveButton;

    @FindBy(id = "credential-url")
    private WebElement url;

    @FindBy(id = "credential-username")
    private WebElement username;

    @FindBy(id = "credential-password")
    private WebElement password;

    @FindBy(id = "credential-modal-button")
    private WebElement submitModalButton;

    public CredentialPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public List<String> getDetail(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.visibilityOf(navCredentialsTab)).click();
        navCredentialsTab.click();
        wait.until(ExpectedConditions.visibilityOf(addCredentials));
        return new ArrayList<>(
                List.of(credentialUrlList.get(0).getText(),
                        credentialUsernameList.get(0).getText(),
                        credentialPasswordList.get(0).getText()));
    }


    public void addCredential(WebDriver driver, String inputUrl, String inputUsername, String inputPassword, WebElement nav) {
        WebDriverWait wait = new WebDriverWait(driver, 15);

        try {
            wait.until(ExpectedConditions.visibilityOf(navCredentialsTab)).click();
        } catch (TimeoutException e) {
            System.out.println("Timeout Exception");
            nav.click();
            wait.until(ExpectedConditions.visibilityOf(navCredentialsTab)).click();
        }

        wait.until(ExpectedConditions.visibilityOf(addCredentials)).click();
        wait.until(ExpectedConditions.visibilityOf(url)).sendKeys(inputUrl);
        wait.until(ExpectedConditions.visibilityOf(username)).sendKeys(inputUsername);
        wait.until(ExpectedConditions.visibilityOf(password)).sendKeys(inputPassword);

        wait.until(ExpectedConditions.visibilityOf(submitModalButton)).click();
    }

    public void editCredential(WebDriver driver, String inputUrl, String inputUsername, String inputPassword) {
        WebDriverWait wait = new WebDriverWait(driver, 15);

        wait.until(ExpectedConditions.visibilityOf(navCredentialsTab)).click();
        wait.until(ExpectedConditions.visibilityOf(editCredential)).click();
        wait.until(ExpectedConditions.visibilityOf(url));

        url.clear();
        url.sendKeys(inputUrl);

        wait.until(ExpectedConditions.visibilityOf(username));
        username.clear();
        username.sendKeys(inputUsername);

        wait.until(ExpectedConditions.visibilityOf(password));
        password.clear();
        password.sendKeys(inputPassword);

        wait.until(ExpectedConditions.visibilityOf(submitModalButton)).click();
    }

    public void deleteCredential(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOf(navCredentialsTab)).click();
        wait.until(ExpectedConditions.visibilityOf(deleteCredential)).click();
    }
}
