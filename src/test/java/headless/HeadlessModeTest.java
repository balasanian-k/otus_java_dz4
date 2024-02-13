package headless;

import Factory.DriverFactory;
import auth.AuthTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import tools.WaitTools;

public class HeadlessModeTest {
    Logger logger = LogManager.getLogger(AuthTest.class);
    private WebDriver driver;
    private String startPointUrl = "https://duckduckgo.com/";

    @BeforeEach
    public void init() {
        driver = new DriverFactory("headless").create();
        driver.get(startPointUrl);
    }

    @AfterEach
    public void stopDriver() {
        if (driver !=null) {
            driver.quit();
        }
    }

    @Test
    public void chromeHeadless() {

        driver.findElement(By.cssSelector("#searchbox_input")).sendKeys("отус" + Keys.ENTER);
        String expectedOtusText = "Онлайн-курсы для профессионалов, дистанционное обучение современным ...";
        WebElement otusLinkText = driver.findElement(By.xpath("//span[text()='Онлайн‑курсы для профессионалов, дистанционное обучение современным ...']"));

        String receivedOtusText = otusLinkText.getText().trim();
        Assertions.assertEquals(expectedOtusText, receivedOtusText );

    }
}
