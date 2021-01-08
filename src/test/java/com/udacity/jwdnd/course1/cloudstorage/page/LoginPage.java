package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

    @FindBy(id = "logout-msg")
    private WebElement logoutMsg;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "signup-link")
    private WebElement signup;

    @FindBy(id = "submit-button")
    private WebElement button;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String getErrorMsg() {
        return errorMsg.getText();
    }

    public String getLogOutMsg() {
        return logoutMsg.getText();
    }

    public void login(String inputUsername, String inputPassword) {
        username.sendKeys(inputUsername);
        password.sendKeys(inputPassword);
        button.click();
    }

    public void gotoSignup() {
        signup.click();
    }


}
