package qa.scooter.pages;

import org.openqa.selenium.By;
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
    private final By metro       = By.xpath("//input[@placeholder='* Станция метро']");
    private final By metroOption = By.xpath("//li[@data-value][1]");
    private final By phone       = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextBtn     = By.xpath("//button[contains(.,'Далее')]");

    // Шаг 2: про аренду
    private final By dateInput        = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalDropdown   = By.className("Dropdown-control");
    private final By rentalDay        = By.xpath("//div[contains(@class,'Dropdown-option') and text()='сутки']");
    private final By rentalTwoDays    = By.xpath("//div[contains(@class,'Dropdown-option') and text()='двое суток']");
    private final By blackColor       = By.id("black");
    private final By greyColor        = By.id("grey");
    private final By commentInput     = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderBtn         = By.xpath("//button[contains(.,'Заказать')]");
    private final By confirmYes       = By.xpath("//button[contains(.,'Да')]");
    private final By successHeader    = By.xpath("//div[contains(text(),'Заказ оформлен')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ---------- Шаг 1 ----------

    private void fillFirstStep(OrderData data) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName))
                .sendKeys(data.getFirstName());
        driver.findElement(lastName).sendKeys(data.getLastName());
        driver.findElement(address).sendKeys(data.getAddress());

        driver.findElement(metro).click();
        wait.until(ExpectedConditions.elementToBeClickable(metroOption)).click();

        driver.findElement(phone).sendKeys(data.getPhone());
        driver.findElement(nextBtn).click();
    }

    // ---------- Шаг 2 ----------

    private void fillSecondStep(OrderData data) {
        // Дата
        WebElement date = wait.until(
                ExpectedConditions.elementToBeClickable(dateInput)
        );
        date.click();
        date.sendKeys(data.getDate());

        // Срок аренды
        driver.findElement(rentalDropdown).click();
        String period = data.getRentalPeriod();
        if ("сутки".equalsIgnoreCase(period)) {
            wait.until(ExpectedConditions.elementToBeClickable(rentalDay)).click();
        } else if ("двое суток".equalsIgnoreCase(period)) {
            wait.until(ExpectedConditions.elementToBeClickable(rentalTwoDays)).click();
        }

        // Цвет: BLACK / GREY / BLACK_GREY
        String color = data.getColor();
        if ("BLACK".equalsIgnoreCase(color) || "BLACK_GREY".equalsIgnoreCase(color)) {
            driver.findElement(blackColor).click();
        }
        if ("GREY".equalsIgnoreCase(color) || "BLACK_GREY".equalsIgnoreCase(color)) {
            driver.findElement(greyColor).click();
        }

        // Комментарий
        driver.findElement(commentInput).sendKeys(data.getComment());

        // Кнопка "Заказать"
        driver.findElement(orderBtn).click();

        // Модалка "Хотите оформить заказ?" — жмём "Да"
        wait.until(ExpectedConditions.elementToBeClickable(confirmYes)).click();
    }

    // Полный флоу
    public void makeOrder(OrderData data) {
        fillFirstStep(data);
        fillSecondStep(data);
    }

    // Проверка успешного заказа
    public boolean isOrderSuccess() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(successHeader)
        ).isDisplayed();
    }
}