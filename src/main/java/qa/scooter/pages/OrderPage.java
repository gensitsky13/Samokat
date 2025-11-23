package qa.scooter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import qa.scooter.model.OrderData;

import java.time.Duration;

public class OrderPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ---------- Локаторы ----------

    // Шаг 1: Для кого самокат
    private final By firstName   = By.xpath("//input[@placeholder='* Имя']");
    private final By lastName    = By.xpath("//input[@placeholder='* Фамилия']");
    private final By address     = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metro       = By.xpath("//input[@placeholder='* Станция метро']");
    private final By metroOption = By.xpath("//li[@data-value][1]");
    private final By phone       = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextBtn     = By.xpath("//button[contains(.,'Далее')]");

    // Шаг 2: Про аренду
    private final By dateInput      = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalDropdown = By.className("Dropdown-control");
    private final By rentalDay      = By.xpath("//div[contains(@class,'Dropdown-option') and text()='сутки']");
    private final By rentalTwoDays  = By.xpath("//div[contains(@class,'Dropdown-option') and text()='двое суток']");
    private final By blackColor     = By.id("black");
    private final By greyColor      = By.id("grey");
    private final By commentInput   = By.xpath("//input[@placeholder='Комментарий для курьера']");

    // Кнопка "Заказать" — в форме "Про аренду"
    private final By orderBtn = By.xpath(
            "//div[contains(@class,'Order_Content')]" +
                    "//div[contains(@class,'Order_Buttons')]" +
                    "//button[contains(@class,'Button_Button') and text()='Заказать']"
    );

    // Кнопка "Да" — в модалке подтверждения
    private final By confirmYes = By.xpath(
            "//div[contains(@class,'Order_Modal')]" +
                    "//div[contains(@class,'Order_Buttons')]" +
                    "//button[text()='Да']"
    );

    // Заголовок успешного заказа
    private final By successHeader = By.xpath("//div[contains(text(),'Заказ оформлен')]");

    // Куки-баннер "Да все привыкли"
    private final By cookieAccept = By.id("rcc-confirm-button");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ---------- Вспомогательные методы ----------

    // Закрываем куки, если внезапно всплыли на этой странице
    private void acceptCookiesIfPresent() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.elementToBeClickable(cookieAccept))
                    .click();
        } catch (Exception ignored) {
            // куки не появились — просто идём дальше
        }
    }

    // ---------- Шаг 1: Для кого самокат ----------

    private void fillFirstStep(OrderData data) {

        // Имя
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName))
                .sendKeys(data.getFirstName());

        // Фамилия
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastName))
                .sendKeys(data.getLastName());

        // Адрес
        wait.until(ExpectedConditions.visibilityOfElementLocated(address))
                .sendKeys(data.getAddress());

        // Станция метро
        wait.until(ExpectedConditions.elementToBeClickable(metro)).click();
        wait.until(ExpectedConditions.elementToBeClickable(metroOption)).click();

        // Телефон
        wait.until(ExpectedConditions.visibilityOfElementLocated(phone))
                .sendKeys(data.getPhone());

        // Кнопка "Далее"
        wait.until(ExpectedConditions.elementToBeClickable(nextBtn)).click();
    }

    // ---------- Шаг 2: Про аренду ----------

    private void fillSecondStep(OrderData data) {

        // Дата
        WebElement date = wait.until(ExpectedConditions.elementToBeClickable(dateInput));
        date.click();
        // очищаем поле на всякий случай
        date.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        date.sendKeys(Keys.DELETE);
        date.sendKeys(data.getDate());
        date.sendKeys(Keys.ENTER); // фиксируем дату

        // Срок аренды
        wait.until(ExpectedConditions.elementToBeClickable(rentalDropdown)).click();
        String period = data.getRentalPeriod();

        if ("сутки".equalsIgnoreCase(period)) {
            wait.until(ExpectedConditions.elementToBeClickable(rentalDay)).click();
        } else if ("двое суток".equalsIgnoreCase(period)) {
            wait.until(ExpectedConditions.elementToBeClickable(rentalTwoDays)).click();
        } else {
            // дефолт — на случай других значений
            wait.until(ExpectedConditions.elementToBeClickable(rentalDay)).click();
        }

        // Цвет: BLACK / GREY / BLACK_GREY
        String color = data.getColor();

        if (color != null) {
            if ("BLACK".equalsIgnoreCase(color) || "BLACK_GREY".equalsIgnoreCase(color)) {
                wait.until(ExpectedConditions.elementToBeClickable(blackColor)).click();
            }
            if ("GREY".equalsIgnoreCase(color) || "BLACK_GREY".equalsIgnoreCase(color)) {
                wait.until(ExpectedConditions.elementToBeClickable(greyColor)).click();
            }
        }

        // Комментарий — после выбора цвета (как просил ревьюер)
        wait.until(ExpectedConditions.visibilityOfElementLocated(commentInput))
                .sendKeys(data.getComment());

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // --- Кнопка "Заказать" ---
        WebElement orderButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(orderBtn)
        );
        // слегка скроллим к кнопке, чтобы её не перекрывал хедер
        js.executeScript("arguments[0].scrollIntoView(true);", orderButton);
        js.executeScript("arguments[0].click();", orderButton);

        // --- Кнопка "Да" в модалке ---
        WebElement yesButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(confirmYes)
        );
        js.executeScript("arguments[0].scrollIntoView(true);", yesButton);
        js.executeScript("arguments[0].click();", yesButton);
    }

    // ---------- Публичный сценарий оформления заказа ----------

    public void makeOrder(OrderData data) {
        // куки могут вылезти на любом шаге — подчищаем
        acceptCookiesIfPresent();
        fillFirstStep(data);

        acceptCookiesIfPresent();
        fillSecondStep(data);

        acceptCookiesIfPresent();
    }

    // ---------- Проверка успешного заказа ----------

    public boolean isOrderSuccess() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(successHeader)
        ).isDisplayed();
    }
}