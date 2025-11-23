package qa.scooter.faq;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.Arguments;
import qa.scooter.BaseUiTest;
import qa.scooter.pages.FaqPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FaqAccordionTest extends BaseUiTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 3, 7})
    @DisplayName("FAQ: раскрытие ответов по клику (0, 3, 7)")
    void faqOpensOnClick(int index) {
        FaqPage faq = new FaqPage(driver);

        faq.toggleQuestion(index);

        assertTrue(
                faq.isAnswerVisible(index),
                "Ответ не раскрылся у вопроса #" + index
        );
    }

    @ParameterizedTest(name = "FAQ: текст ответа у вопроса #{0} соответствует ожиданию")
    @MethodSource("faqAnswers")
    void checkFaqAnswerText(int index, String expectedAnswer) {
        FaqPage faq = new FaqPage(driver);

        faq.toggleQuestion(index);
        String actual = faq.getAnswerText(index);

        assertEquals(
                expectedAnswer,
                actual,
                "Неверный текст ответа у вопроса #" + index
        );
    }

    // Источник данных для параметризованного теста с текстами ответов
    private Stream<Arguments> faqAnswers() {
        return Stream.of(
                Arguments.of(0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."),
                Arguments.of(1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."),
                Arguments.of(2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."),
                Arguments.of(3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."),
                Arguments.of(4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."),
                Arguments.of(5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."),
                Arguments.of(6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."),
                Arguments.of(7, "Да, обязательно. Всем самокатов! И Москве, и Московской области.")
        );
    }
}