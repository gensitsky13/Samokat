package qa.scooter.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Верхняя кнопка "Заказать"
    private final By topOrderBtn    = By.xpath("(//button[text()='Заказать'])[1]");
    // Нижняя кнопка "Заказать"
    private final By bottomOrderBtn = By.xpath("(//button[text()='Заказать'])[2]");
    private final By cookieAccept   = By.id("rcc-confirm-button");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public HomePage open(String baseUrl) {
        driver.get(baseUrl);
        return this;
    }

    public void acceptCookiesIfPresent() {
        try {
            wait.withTimeout(Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(cookieAccept))
                    .click();
        } catch (TimeoutException ignored) {
            // куки просто не показались — ок
        }
    }

    // Клик по ВЕРХНЕЙ кнопке "Заказать"
    public void clickTopOrderButton() {
        WebElement button = wait.until(ExpectedConditions
                .elementToBeClickable(topOrderBtn));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", button);
        button.click();
    }

    // Клик по НИЖНЕЙ кнопке "Заказать"
    public void clickBottomOrderButton() {
        WebElement button = wait.until(ExpectedConditions
                .elementToBeClickable(bottomOrderBtn));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", button);
        button.click();
    }
}