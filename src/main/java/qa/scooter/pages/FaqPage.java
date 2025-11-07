package qa.scooter.pages;

import org.openqa.selenium.WebDriver;

public class FaqPage {
    private final HomePage home;

    public FaqPage(WebDriver driver) {
        this.home = new HomePage(driver);
    }

    public void toggleQuestion(int index) {
        home.toggleFaqByIndex(index);
    }

    public boolean isAnswerVisible(int index) {
        return home.getFaqAnswerByIndex(index).isDisplayed();
    }
}
