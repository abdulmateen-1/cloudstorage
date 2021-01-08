package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

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

}
