package qa.scooter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FaqPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы FAQ
    private final By question = By.xpath("//div[@class='accordion__button']");
    private final By answer   = By.xpath("//div[@class='accordion__panel']");

    public FaqPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // Клик по вопросу по индексу
    public void toggleQuestion(int index) {
        WebElement q = driver.findElements(question).get(index);
        wait.until(ExpectedConditions.elementToBeClickable(q)).click();
    }

    // Получить WebElement ответа
    private WebElement getAnswerElement(int index) {
        return driver.findElements(answer).get(index);
    }

    // Проверить, что ответ отображается
    public boolean isAnswerVisible(int index) {
        WebElement ans = getAnswerElement(index);
        wait.until(ExpectedConditions.visibilityOf(ans));
        return ans.isDisplayed();
    }

    // Получить текст ответа
    public String getAnswerText(int index) {
        WebElement ans = getAnswerElement(index);
        wait.until(ExpectedConditions.visibilityOf(ans));
        return ans.getText().trim();
    }
}