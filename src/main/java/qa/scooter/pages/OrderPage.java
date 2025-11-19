package qa.scooter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import qa.scooter.model.OrderData;

import java.time.Duration;

public class OrderPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Шаг 1: Для кого самокат
    private final By firstName   = By.xpath("//input[@placeholder='* Имя']");
    private final By lastName    = By.xpath("//input[@placeholder='* Фамилия']");
    private final By address     = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroInput  = By.xpath("//input[@placeholder='* Станция метро']");
    private final By metroOption = By.xpath("//li[@data-value][1]");
    private final By phone       = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextBtn     = By.xpath("//button[contains(.,'Далее')]");

    // Шаг 2: Про аренду
    private final By dateInput      = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalDropdown = By.className("Dropdown-control");

    // Цвета самоката
    private final By blackColor = By.id("black");
    private final By greyColor  = By.id("grey");

    // Остальные элементы
    private final By comment    = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderBtn   = By.cssSelector(".Button_Middle__1CSM:nth-child(2)");
    private final By confirmYes = By.xpath("//button[text()='Да']");

    // Модалка успешного заказа
    private final By successHeader = By.xpath("//div[contains(text(),'Заказ оформлен')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /** Шаг 1: "Для кого самокат" */
    private void fillFirstStep(OrderData data) {

        // Имя
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName))
                .sendKeys(data.getFirstName());

        // Фамилия
        driver.findElement(lastName).sendKeys(data.getLastName());

        // Адрес
        driver.findElement(address).sendKeys(data.getAddress());

        // Станция метро
        driver.findElement(metroInput).click();
        wait.until(ExpectedConditions.elementToBeClickable(metroOption)).click();

        // Телефон
        driver.findElement(phone).sendKeys(data.getPhone());

        // Кнопка "Далее"
        driver.findElement(nextBtn).click();
    }

    /** Шаг 2: "Про аренду" */
    private void fillSecondStep(OrderData data) {

        // Дата
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateInput))
                .sendKeys(data.getDate());
        driver.findElement(dateInput).sendKeys(Keys.ENTER); // закрыть календарь

        // Срок аренды
        driver.findElement(rentalDropdown).click();
        try {
            WebElement durationOption = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[@class='Dropdown-menu']/div[text()='" + data.getDuration() + "']"))
            );
            durationOption.click();
        } catch (TimeoutException e) {
            // Если нужного текста нет — берём первый вариант
            WebElement firstOption = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[@class='Dropdown-menu']/div[1]"))
            );
            firstOption.click();
        }

        // Цвет: BLACK / GREY / BLACK_GREY
        String color = data.getColor();
        if ("BLACK".equalsIgnoreCase(color) || "BLACK_GREY".equalsIgnoreCase(color)) {
            driver.findElement(blackColor).click();
        }
        if ("GREY".equalsIgnoreCase(color) || "BLACK_GREY".equalsIgnoreCase(color)) {
            driver.findElement(greyColor).click();
        }

        // Комментарий курьеру
        driver.findElement(comment).sendKeys(data.getComment());

        // "Заказать" → "Да"
        driver.findElement(orderBtn).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmYes)).click();
    }

    /** Полный флоу оформления заказа */
    public void makeOrder(OrderData data) {
        fillFirstStep(data);
        fillSecondStep(data);
    }

    /** Проверка успешного заказа */
    public boolean isOrderSuccess() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successHeader)).isDisplayed();
    }
}