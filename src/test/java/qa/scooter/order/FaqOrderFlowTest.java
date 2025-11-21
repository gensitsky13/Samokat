package qa.scooter.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import qa.scooter.BaseUiTest;
import qa.scooter.model.OrderData;
import qa.scooter.pages.HomePage;
import qa.scooter.pages.OrderPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FaqOrderFlowTest extends BaseUiTest {

    // 👉 2 набора данных для заказа
    static Stream<OrderData> orderData() {
        return Stream.of(
                    new OrderData(
                            "Иван",                 // firstName
                            "Иванов",               // lastName
                            "Москва, Тверская 1",   // address
                            "Черкизовская",         // metro
                            "+79990000001",         // phone
                            "10.12.2025",           // date
                            "сутки",                // duration
                            "BLACK",                // color  ✔️ сначала цвет
                            "Позвонить заранее"     // comment ✔️ потом комментарий
                    ),
                    new OrderData(
                            "Ольга",                // firstName
                            "Иванова",              // lastName
                            "Москва, Тверская 10",  // address
                            "Нагорная",             // metro
                            "+79990000009",         // phone
                            "18.12.2026",           // date
                            "сутки",                // duration
                            "BLACK",                // color  ✔️
                            "Позвонить заранее"     // comment ✔️
                    )
            );
        }



    @ParameterizedTest
    @MethodSource("orderData")
    @DisplayName("Order: позитивный сценарий — вход сверху (2 набора данных)")
    void orderFlowTopButton(OrderData data) {
        HomePage home = new HomePage(driver)
                .open(BASE_URL);
        home.acceptCookiesIfPresent();

        // ❗ Тест идёт через ВЕРХНЮЮ кнопку "Заказать"
        home.clickTopOrderButton();

        OrderPage orderPage = new OrderPage(driver);
        orderPage.makeOrder(data);

        assertTrue(orderPage.isOrderSuccess(),
                "Модальное окно с подтверждением заказа не появилось (верхняя кнопка)");
    }

    @ParameterizedTest
    @MethodSource("orderData")
    @DisplayName("Order: позитивный сценарий — вход снизу (2 набора данных)")
    void orderFlowBottomButton(OrderData data) {
        HomePage home = new HomePage(driver)
                .open(BASE_URL);
        home.acceptCookiesIfPresent();

        // Вход через НИЖНЮЮ кнопку "Заказать"
        home.clickBottomOrderButton();

        OrderPage orderPage = new OrderPage(driver);
        orderPage.makeOrder(data);

        assertTrue(orderPage.isOrderSuccess(),
                "Модальное окно с подтверждением заказа не появилось (нижняя кнопка)");
    }
}