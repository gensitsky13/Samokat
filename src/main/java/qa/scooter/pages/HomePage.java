package qa.scooter.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Кнопки "Заказать" сверху и снизу
    private final By topOrderBtn    = By.xpath("(//button[contains(.,'Заказать')])[1]");
    private final By bottomOrderBtn = By.xpath("(//button[contains(.,'Заказать')])[last()]");

    // Куки
    private final By cookieAccept   = By.id("rcc-confirm-button");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /** Открыть главную страницу */
    public HomePage open(String baseUrl) {
        driver.get(baseUrl);
        return this;
    }

    /** Принять куки, если баннер появился */
    public void acceptCookiesIfPresent() {
        try {
            wait.withTimeout(Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(cookieAccept))
                    .click();
        } catch (TimeoutException ignored) {
            // баннер не появился — ничего не делаем
        }
    }

    /** Клик по верхней кнопке "Заказать" */
    public void clickTopOrderButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(topOrderBtn));
        // Скролл, чтобы в Chrome тест проверял реальное поведение (и падал при баге)
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
    }

    /** Клик по нижней кнопке "Заказать" */
    public void clickBottomOrderButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(bottomOrderBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
    }
}