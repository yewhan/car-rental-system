import javax.swing.*;
import java.awt.*;

public class PaymentConfirmation extends JFrame {

    private JPanel panelMain;
    private JButton btnYes;
    private JButton btnNo;
    private JLabel lblAuthorize;

    public PaymentConfirmation(JFrame checkout, String license, String carReg, String returnDate) {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 200));
        pack();

        btnNo.addActionListener(e -> {
            dispose();
            checkout.setVisible(true);
        });

        btnYes.addActionListener(e -> {
            printReceipt(license, carReg, returnDate);
            checkout.dispose();
            dispose();
        });
    }

    public static void printReceipt(String license, String carReg, String returnDate) {
        CustomerController.addCarToCustomer(license, carReg); //add car reg to customer's account
        CustomerController.saveCustomer(); //update customer database
        HomeScreen.stock.setCarToUnavailable(carReg, returnDate); //set bought car to unavailable and add new return date
        HomeScreen.stock.saveStock(); //update car database

        Receipt receipt = new Receipt(license, carReg);
        receipt.setVisible(true);
    }
}
