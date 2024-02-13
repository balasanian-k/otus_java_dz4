package kiosk;

import auth.AuthTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tools.WaitTools;

import java.util.List;

public class KioskModeTest {

    Logger logger = LogManager.getLogger(AuthTest.class);

    private String kioskUrl = "https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818";

    private WebDriver driver;
    private WaitTools waitTools;


    @BeforeEach
    public void init() {
        driver = new ChromeDriver();
        waitTools = new WaitTools(driver);
        driver.get(kioskUrl);
    }

    @AfterEach
    public void stopDriver() {
        if (driver !=null) {
            driver.quit();
        }
    }

    @Test
    public void modalWindow() {

        List<WebElement> imgList = driver.findElements(By.cssSelector("div.content-overlay"));

        logger.info("Image is visible. Lets continue the test");
        waitTools.waitForCondition(ExpectedConditions.stalenessOf(imgList.get(0)));

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView()", imgList.get(0));

        imgList.get(0).click();

        WebElement modalWindowExp = driver.findElement(By.cssSelector("div.pp_hoverContainer"));

        logger.info("Expanded image is visible. Lets continue the test");
        waitTools.waitForCondition(ExpectedConditions.stalenessOf(modalWindowExp));

        boolean factResult = driver.findElement(By.cssSelector("div.pp_pic_holder.light_rounded")).isDisplayed();
        Assertions.assertTrue(factResult);
        logger.info("Test passed. Applause!");

    }
}
