package qa.scooter.faq;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.scooter.BaseUiTest;
import qa.scooter.pages.FaqPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FaqAccordionTest extends BaseUiTest {

    @Test
    @DisplayName("FAQ: раскрытие ответов по клику (вопросы 0, 3, 7)")
    void faqOpensOnClick() {
        int[] indexes = {0, 3, 7};
        FaqPage faq = new FaqPage(driver);
        for (int index : indexes) {
            faq.toggleQuestion(index);
            assertTrue(faq.isAnswerVisible(index), "Ответ не раскрылся у вопроса #" + index);
        }
    }
}