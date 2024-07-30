package headless;

import Factory.DriverFactory;
import fullscreen.FullscreenModeTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tools.WaitTools;

public class HeadlessModeTest {
    Logger logger = LogManager.getLogger(FullscreenModeTest.class);
    private WebDriver driver;
    private String startPointUrl = "https://duckduckgo.com/";

    private WaitTools waitTools;

    @BeforeEach
    public void init() {
        driver = new DriverFactory("--start-maximized").create();
        waitTools = new WaitTools(driver);
        driver.get(startPointUrl);
    }

    @AfterEach
    public void stopDriver() {
        if (driver !=null) {
            logger.info("Разрываем соединение");
            driver.quit();
        }
    }

    @Test
    public void chromeHeadless() {

        driver.findElement(By.cssSelector("#searchbox_input")).sendKeys("отус" + Keys.ENTER);

        String expectedOtusText = "Онлайн‑курсы для профессионалов, дистанционное обучение современным ...";
        waitTools.waitForCondition(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Онлайн‑курсы для профессионалов, дистанционное обучение современным ...']")));
        WebElement otusLinkText = driver.findElement(By.xpath("//span[text()='Онлайн‑курсы для профессионалов, дистанционное обучение современным ...']"));

        String receivedOtusText = otusLinkText.getText().trim();
        Assertions.assertEquals(expectedOtusText, receivedOtusText );

    }
}
