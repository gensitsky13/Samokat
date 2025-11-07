package qa.scooter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import qa.scooter.pages.DriverFactory;
import qa.scooter.pages.HomePage;

import java.time.Duration;

public abstract class BaseUiTest {
    protected WebDriver driver;
    protected HomePage home;
    protected final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    @BeforeEach
    void setUp() {
        driver = DriverFactory.create();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();

        home = new HomePage(driver).open(BASE_URL);
        home.acceptCookiesIfPresent();
    }

    @AfterEach
    void tearDown() {
         driver.quit();
    }
}