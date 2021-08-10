import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StaffControllerTest {

    private final StaffController staff = new StaffController();


    @BeforeEach
    void setUp() {
        StaffAccounts staff1 = new StaffAccounts("staff1", "password1");
        staff.staffList = new ArrayList<>();
        staff.staffList.add(staff1);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void checkLogin() {
        assertTrue(staff.checkLogin("staff1", "password1"));
    }
}