package qa.scooter.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import qa.scooter.model.OrderData;

import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Шаг 1: Для кого самокат
    private final By firstName = By.xpath("//input[@placeholder='* Имя']");
    private final By lastName  = By.xpath("//input[@placeholder='* Фамилия']");
    private final By address   = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metro     = By.xpath("//input[@placeholder='* Станция метро']");
    private final By metroOption = By.xpath("//li[@data-value][1]"); // первая станция в списке
    private final By phone     = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextBtn   = By.xpath("//button[contains(.,'Далее')]");

    // Шаг 2: Про аренду
    private final By dateInput = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By durationDropdown = By.xpath("//div[@class='Dropdown-root']");
    private final String durationOptionTpl = "//div[@class='Dropdown-option' and text()='%s']";
    private final By colorBlack = By.id("black");
    private final By colorGrey  = By.id("grey");
    private final By comment    = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderBtn   = By.cssSelector(".Button_Middle__1CSJM:nth-child(2)");
    private final By confirmYes = By.xpath("//button[text()='Да']");
    private final By successModal = By.xpath("//div[contains(@class,'Order_Modal')]");
    private final By successText  = By.xpath("//*[contains(text(),'Заказ оформлен')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    public OrderPage fillCustomerInfo(OrderData d) {
        wait.until(ExpectedConditions.elementToBeClickable(firstName)).sendKeys(d.firstName);
        driver.findElement(lastName).sendKeys(d.lastName);
        driver.findElement(address).sendKeys(d.address);

        driver.findElement(metro).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(metroOption)).click();
        driver.findElement(phone).sendKeys(d.phone);

        driver.findElement(nextBtn).click();
        return this;
    }

    public OrderPage fillRentalInfo(OrderData d) {
        wait.until(ExpectedConditions.elementToBeClickable(dateInput)).click();
        driver.findElement(dateInput).sendKeys(d.date);
        driver.findElement(dateInput).sendKeys(Keys.ENTER);

        driver.findElement(durationDropdown).click();
        driver.findElement(By.xpath(String.format(durationOptionTpl, d.duration))).click();

        if ("black".equalsIgnoreCase(d.color)) driver.findElement(colorBlack).click();
        if ("grey".equalsIgnoreCase(d.color))  driver.findElement(colorGrey).click();

        driver.findElement(comment).sendKeys(d.comment);
        return this;
    }

    public void submitOrder() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.findElement(orderBtn).click();

        wait.until(ExpectedConditions.elementToBeClickable(confirmYes)).click();
    }

    public boolean isSuccessModalVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successModal));
            return driver.findElements(successText).size() > 0
                    || driver.findElements(successModal).size() > 0;
        } catch (TimeoutException e) {
            return false;
        }
    }
}