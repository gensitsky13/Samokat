package qa.scooter.pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
    public static WebDriver create() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions ff = new FirefoxOptions();


                ff.setBinary("/Applications/Firefox.app/Contents/MacOS/firefox");

                if (headless) {
                    ff.addArguments("--headless");
                }

                return new FirefoxDriver(ff);
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions ch = new ChromeOptions();
                if (headless) ch.addArguments("--headless=new");
                ch.addArguments("--window-size=1366,900", "--no-sandbox", "--disable-gpu");
                return new ChromeDriver(ch);
        }
    }
}