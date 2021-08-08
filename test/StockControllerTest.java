import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StockControllerTest {

    @BeforeEach
    void setUp() {
        Stock car1 = new Stock(true, "test", "123", 15.50F);
        Stock car2 = new Stock(false, "test2", "1234", 20.00f, LocalDate.of(2030, 1, 1));

        StockController.carList = new ArrayList<>();

        StockController.carList.add(car1);
    }

    @Test
    void getCarDetails() {
        assertTrue(StockController.getCarDetails("123").length > 0);
    }

    @Test
    void checkReg() {
        assertTrue(StockController.checkReg("123"));
    }

    @Test
    void calculateTotalPrice() {
        assertTrue(StockController.calculateTotalPrice("123", (LocalDate.now())).isBlank());
    }

    @Test
    void checkAvailability() {
        assertFalse(StockController.checkAvailability("1234"));
    }
}