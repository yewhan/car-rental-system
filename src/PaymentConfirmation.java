import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        btnNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                checkout.setVisible(true);
            }
        });

        btnYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printReceipt(license, carReg, returnDate);
            }
        });
    }

    public static void printReceipt(String license, String carReg, String returnDate) {
        AccountsController.addCarToCustomer(license, carReg);
        StockController.addCustomerToCar(license, carReg, returnDate);
    }
}
