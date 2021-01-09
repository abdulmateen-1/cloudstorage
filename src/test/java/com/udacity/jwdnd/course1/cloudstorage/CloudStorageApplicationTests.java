package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.*;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	public int port;
	public String BaseUrl;

	public static WebDriver driver;

	public SignupPage signupPage;
	public LoginPage loginPage;
	public HomePage homePage;
	public NotePage notePage;
	public CredentialPage credentialPage;


	public WebDriverWait wait;
	public WebElement element;
	public Boolean work;

	String firstName = "matin";
	String lastName = "Ola";
	String username = "olaM2";
	String password = "pass234";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@BeforeEach
	public void beforeEach() {
		BaseUrl = "http://localhost:" + port;
		wait = new WebDriverWait(driver, 100);
	}

	@AfterAll
	public static void afterAll() {
		if (driver != null) {
			driver.quit();
		}
		driver = null;
	}

	@Test
	@Order(1)
	public void testHomeIsInAccessibleWithoutSignIn(){
		driver.get(BaseUrl + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	@Order(2)
	public void getLoginPage() {
		driver.get(BaseUrl + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(3)
	public void testValidSignupAndLoginAndLogout() {
		//To check whether the signup page is loading
		driver.get(BaseUrl + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		//once the page is loaded we sign in
		signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);

		//once user is successfully signed in we should bw able to login successfully
		driver.get(BaseUrl + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());

		//Once successfully in the Home Page we want to be able to logout
		driver.get(BaseUrl + "/home");
		homePage = new HomePage(driver);
		homePage.gotoLogout();
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(4)
	public void testInvalidSignUpAndLogin() {
		//Get the signup Page for thw user
		driver.get(BaseUrl+ "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		//Load in the sign-in information
		signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);

		//Log in with an incorrect credential
		driver.get(BaseUrl + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage = new LoginPage(driver);
		loginPage.login(firstName, password);

		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	@Order(5)
	public void loginFunction() {
		driver.get(BaseUrl + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);

		driver.get(BaseUrl + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	@Order(6)
	public void testEditAndDeleteNote() throws InterruptedException {
		loginFunction();
		notePage = new NotePage(driver);
		Thread.sleep(1000);
		WebElement navigation = driver.findElement(By.id("nav-notes-tab"));
		navigation.click();

		//Add note
		notePage.addNote(driver, "Title buzziness", "Thiz iz de dizcriptionz", navigation);
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("success-redirect"))).click();
		driver.get(BaseUrl + "/home");

		List<String> detail = notePage.getDetail(driver);
		Assertions.assertEquals("Title buzziness", detail.get(0));
		Assertions.assertEquals("Thiz iz de dizcriptionz", detail.get(1));

		//Edit Note
		notePage.editNote(driver, "Edit Titlez", "Ediz Dezcriptionz");
		driver.get(BaseUrl + "/home");
		detail = notePage.getDetail(driver);

		Assertions.assertEquals("Edit Titlez", detail.get(0));
		Assertions.assertEquals("Ediz Dezcriptionz", detail.get(1));

		//Delete Note
		notePage.deleteNote(driver);
		driver.get(BaseUrl + "/home");
		wait.until(driver1 -> driver.findElement(By.id("nav-notes-tab")));

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println("Stack Message: " + e.getMessage());
		}


	}

	@Test
	@Order(7)
	public void testAddEditDeleteCredentials() throws Exception {
		loginFunction();


		//Add new Credentials
		credentialPage = new CredentialPage(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		Thread.sleep(1000);
		WebElement navigation = driver.findElement(By.id("nav-credentials-tab"));
		navigation.click();

		credentialPage.addCredential(driver,
				"www.facebook.com",
				"userDegozaru",
				"pass1234",
				navigation);
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("success-redirect"))).click();
		driver.get(BaseUrl + "/home");

		List<String> detail = credentialPage.getDetail(driver);
		Assertions.assertEquals("www.facebook.com", detail.get(0));
		Assertions.assertEquals("userDegozaru", detail.get(1));
//		Assertions.assertEquals("pass1234r", detail.get(2)); How to decrypt password

		//Edit the user name
		credentialPage.editCredential(driver,
				"www.buzz.com",
				"whatzyourz",
				"pazz");
		driver.get(BaseUrl + "/home");
		detail = credentialPage.getDetail(driver);

		Assertions.assertEquals("www.buzz.com", detail.get(0));
		Assertions.assertEquals("whatzyourz", detail.get(1));
	}

}
