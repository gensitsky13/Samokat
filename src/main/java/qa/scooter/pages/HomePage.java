package qa.scooter.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By topOrderBtn    = By.xpath("(//button[contains(.,'Заказать')])[1]");
    private final By bottomOrderBtn = By.xpath("(//button[contains(.,'Заказать')])[last()]");
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
        } catch (Exception ignored) {}
    }

    public void clickTopOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(topOrderBtn)).click();
    }

    public void clickBottomOrder() {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(bottomOrderBtn));
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);"); // прокрутка в самый низ
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
        wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
    }

    // ===== FAQ =====
    public WebElement getFaqQuestionByIndex(int index) {
        By q = By.id("accordion__heading-" + index);
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(q));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        return el;
    }

    public WebElement getFaqAnswerByIndex(int index) {
        By a = By.id("accordion__panel-" + index);
        return wait.until(ExpectedConditions.presenceOfElementLocated(a));
    }

    public void toggleFaqByIndex(int index) {
        WebElement question = wait.until(ExpectedConditions.elementToBeClickable(By.id("accordion__heading-" + index)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", question);
        question.click();
    }
}