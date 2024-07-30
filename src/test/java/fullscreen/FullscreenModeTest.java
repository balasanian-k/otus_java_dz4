package fullscreen;

import Factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tools.WaitTools;

public class FullscreenModeTest {

    Logger logger = (Logger) LogManager.getLogger(FullscreenModeTest.class);

    private String baseUrl = System.getProperty("base.url");
    private String login = System.getProperty("login");
    private String password = System.getProperty("password");

    private WebDriver driver;

    private WaitTools waitTools;

    @BeforeEach
      public void init() {
        driver = new DriverFactory("--start-maximized").create();
        waitTools = new WaitTools(driver);
        driver.get(baseUrl);
    }

    @Test
      public void loginUser() {

        String signInButtonLocator = "//button[text()='Войти']";
        waitTools.waitForCondition(ExpectedConditions.presenceOfElementLocated(By.xpath(signInButtonLocator)));
        waitTools.waitForCondition(ExpectedConditions.elementToBeClickable(By.xpath(signInButtonLocator)));

        WebElement signInButton = driver.findElement(By.xpath(signInButtonLocator));

        String signInPopupSelector = "#__PORTAL__ >div";

        Assertions.assertTrue(waitTools.waitForCondition(ExpectedConditions.not(ExpectedConditions.
                visibilityOfElementLocated(By.cssSelector(signInPopupSelector)))),"Error");

        signInButton.click();

        WebElement authPopupElement = driver.findElement(By.cssSelector(signInPopupSelector));

        Assertions.assertTrue(waitTools.waitForCondition(ExpectedConditions.visibilityOf(authPopupElement)), "");


        WebElement emailInputField = driver.findElement(By.cssSelector("input[name='email']"));
        driver.findElement(By.xpath("//div[./input[@name='email']]")).click();
        waitTools.waitForCondition(ExpectedConditions.stalenessOf(emailInputField));

        emailInputField.sendKeys(login);


        WebElement passwordInputField = driver.findElement(By.cssSelector("input[type='password']"));
        driver.findElement(By.xpath("//div[./input[@type='password']]")).click();
        waitTools.waitForCondition(ExpectedConditions.stalenessOf(passwordInputField));

        passwordInputField.sendKeys(password);


        driver.findElement(By.cssSelector("#__PORTAL__ button")).click();

        logger.info("Authorization is successful. Continue the test");
        Assertions.assertTrue(waitTools.waitForCondition(ExpectedConditions.not(ExpectedConditions.
                visibilityOfElementLocated(By.xpath(signInButtonLocator)))));

        String cookies = driver.manage().getCookies().toString();
        logger.info("Cookies: " + cookies);
        logger.info("Test passed. Hooray!");
    }
}
