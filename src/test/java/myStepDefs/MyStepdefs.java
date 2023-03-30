package myStepDefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyStepdefs {

    WebDriver driver;
    private WebDriverWait wait;


    @Given("I chooses driver {string} and enter ChimpSite")
    public void iChoosesWebdriverString(String Driver) {

        if (Driver.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", "C:\\Selenium\\msedgedriver.exe");
            driver = new EdgeDriver();

        } else if (Driver.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);

        }
        driver.get("https://login.mailchimp.com/signup/");
        driver.manage().window().maximize();
    }

    @When("I input a new {string}")
    public void iAddNewEmail(String Email) {

        waitSendKeys(driver, By.cssSelector(("[type=\"email\"]")), Email);
    }

    @When("I add a random {int} username")
    public void iAddUserName(int Length) {
        WebElement UserName = driver.findElement(By.id("new_username"));
        UserName.click();
        UserName.clear();

        if (Length == 0) {

            String NameName = "dfvdvdsv@gmial.com";
            UserName.sendKeys(NameName);

        } else {

            int lenght = Length;
            String User = "";
            String alphabet = "ABCDEFGHIJKLMNOPQURSTWXYabcdefghijklmnopqurstwxy";

            for (int i = 0; i < lenght; i++) {

                User += alphabet.charAt((int) (Math.random() * alphabet.length()));
            }

            UserName.sendKeys(User + "Linus");

        }
    }

    @When("I chooses a {string}")
    public void iChoosesPassWord(String Password) {

        waitSendKeys(driver, By.id(("new_password")), Password);

    }

    @When("I push the Sign Up button")
    public void iSignUp() {

        WebElement SignUpbutton = driver.findElement(By.cssSelector("[value=\"Create My Account\"]"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)", "");
        SignUpbutton.click();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        SignUpbutton.click();
    }

    @Then("I {string} to create a account")
    public void CheckToCreateAAccount(String Attempt) {

        if (Attempt.equals("OK")) {

            wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class=\"margin-bottom--lv5\"]")));

            String actual = driver.findElement(By.cssSelector("[class=\"!margin-bottom--lv3 no-transform center-on-medium \"]")).getText();
            String expected = "Check your email";

            assertEquals(actual, expected);

        } else if (Attempt.equals("FAIL")) {

            WebElement pressSignUp = driver.findElement(By.id("create-account-enabled"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)", "");
            pressSignUp.click();

            wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class='invalid-error']")));

            String errorMessage = driver.findElement(By.cssSelector("[class='invalid-error']")).getText();
            System.out.println(errorMessage);

            String actual = driver.getTitle();
            String expected = "Signup | Mailchimp";
            String expectedMessage = "";

            assertEquals(actual, expected);

            if (errorMessage.equals("An email address must contain a single @.")) {
                expectedMessage = "An email address must contain a single @.";

            }
            if (errorMessage.equals("Enter a value less than 100 characters long")) {
                expectedMessage = "Enter a value less than 100 characters long";
            }
            if (errorMessage.equals("Great minds think alike - someone already has this username. If it's you, log in.")) {
                expectedMessage = "Great minds think alike - someone already has this username. If it's you, log in.";
            }

            assertEquals(errorMessage, expectedMessage);
        }
    }

    @After
    public void TearDown() {

        driver.close();
        driver.quit();
    }

    private void waitSendKeys(WebDriver driver, By by, String text) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        element.sendKeys(text);
    }

}


