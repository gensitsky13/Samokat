package qa.scooter.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.scooter.BaseUiTest;
import qa.scooter.model.OrderData;
import qa.scooter.pages.OrderPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FaqOrderFlowTest extends BaseUiTest {

    private void runOrderFlowFromTop(OrderData d) {
        home.clickTopOrder();
        OrderPage order = new OrderPage(driver)
                .fillCustomerInfo(d)
                .fillRentalInfo(d);
        order.submitOrder();
        assertTrue(order.isSuccessModalVisible(),
                "Ожидали модалку об успешном создании заказа — возможно, воспроизвёлся баг в Chrome.");
    }

    private void runOrderFlowFromBottom(OrderData d) {
        home.clickBottomOrder();
        OrderPage order = new OrderPage(driver)
                .fillCustomerInfo(d)
                .fillRentalInfo(d);
        order.submitOrder();
        assertTrue(order.isSuccessModalVisible(),
                "Ожидали модалку об успешном создании заказа — возможно, воспроизвёлся баг в Chrome.");
    }

    @Test
    @DisplayName("Order: позитивный сценарий — вход сверху (2 набора данных)")
    void orderFlowTopButton() {
        runOrderFlowFromTop(new OrderData("Анна","Иванова","г. Москва, Тверская 1","Тверская",
                "+79990000001","20.11.2025","двое суток","black","Позвоните за 30 минут"));
        runOrderFlowFromTop(new OrderData("Олег","Каспи","г. Москва, Арбат 2","Арбатская",
                "+79990000002","21.11.2025","сутки","grey","Оставить у консьержа"));
    }

    @Test
    @DisplayName("Order: позитивный сценарий — вход снизу (2 набора данных)")
    void orderFlowBottomButton() {
        runOrderFlowFromBottom(new OrderData("Анна","Иванова","г. Москва, Тверская 1","Тверская",
                "+79990000001","20.11.2025","двое суток","black","Позвоните за 30 минут"));
        runOrderFlowFromBottom(new OrderData("Олег","Каспи","г. Москва, Арбат 2","Арбатская",
                "+79990000002","21.11.2025","сутки","grey","Оставить у консьержа"));
    }
}