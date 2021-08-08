import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountsControllerTest {



    @BeforeEach
    void setUp() {
        StaffAccounts staff1 = new StaffAccounts("staff1", "password1");
        CustomerAccounts customer1 = new CustomerAccounts("james", "bond", "1 test drive", "123", "abcd");
        CustomerAccounts customer2 = new CustomerAccounts("robert", "plant", "2 test drive", "1234");


        AccountsController.staffList = new ArrayList<>();
        AccountsController.customersList = new ArrayList<>();

        AccountsController.staffList.add(staff1);
        AccountsController.customersList.add(customer1);
        AccountsController.customersList.add(customer2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void checkLogin() {
        assertTrue(AccountsController.checkLogin("staff1", "password1"));
    }

    @Test
    void checkIfCustomerExists() {
        assertTrue(AccountsController.checkIfCustomerExists("123"));
    }

    @Test
    void checkIfCustomerHasCar() {
        assertEquals("abcd", AccountsController.checkIfCustomerHasCar("123"));
    }

    @Test
    void checkWhoHasCar() {
        assertEquals("james bond", AccountsController.checkWhoHasCar("abcd"));
    }

    @Test
    void editCustomer() {
        assertTrue(AccountsController.editCustomer("james bond", "3 test drive", "123"));
    }

    @Test
    void getCustomerName() {
        String[] $temp = AccountsController.getCustomerName("123");
        assertTrue($temp[0].equalsIgnoreCase("James") && $temp[1].equalsIgnoreCase("Bond"));
    }
}