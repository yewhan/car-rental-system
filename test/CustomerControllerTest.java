import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CustomerControllerTest {
    

    @BeforeEach
    void setUp() {
        CustomerAccounts customer1 = new CustomerAccounts("james", "bond", "1 test drive", "123", "abcd");
        CustomerAccounts customer2 = new CustomerAccounts("robert", "plant", "2 test drive", "1234");

        CustomerController.customersList = new ArrayList<>();

        CustomerController.customersList.add(customer1);
        CustomerController.customersList.add(customer2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void checkIfCustomerExists() {
        assertTrue(CustomerController.checkIfCustomerExists("123"));
    }

    @Test
    void checkIfCustomerHasCar() {
        assertEquals("abcd", CustomerController.checkIfCustomerHasCar("123"));
    }

    @Test
    void checkWhoHasCar() {
        assertEquals("james bond", CustomerController.checkWhoHasCar("abcd"));
    }

    @Test
    void editCustomer() {
        assertTrue(CustomerController.editCustomer("james bond", "3 test drive", "123"));
    }

    @Test
    void getCustomerName() {
        String[] $temp = CustomerController.getCustomerName("123");
        assertTrue($temp[0].equalsIgnoreCase("James") && $temp[1].equalsIgnoreCase("Bond"));
    }
}