import javax.swing.*;
import java.awt.*;

public class Checkout extends JFrame {

    private JPanel panelMain;
    private JLabel lblBalance;
    private JLabel lblPrice;
    private JButton btnBack;
    private JButton btnPay;
    private final JFrame frameMain;

    public Checkout(JFrame customerDetails, String license, String totalCost, String carReg, String returnDate) {
        lblPrice.setText(totalCost); //display total cost to user

        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(475, 200));
        pack();
        frameMain = this;

        btnBack.addActionListener(e -> {
            dispose();
            customerDetails.setVisible(true);
        });

        btnPay.addActionListener(e -> openPaymentConfirmation(frameMain, license, carReg, returnDate));
    }

    public void openPaymentConfirmation(JFrame frameMain, String license, String carReg, String returnDate) { //prompt for payment confirmation
        this.setVisible(false);
        PaymentConfirmation confirmation = new PaymentConfirmation(frameMain, license, carReg, returnDate);
        confirmation.setVisible(true);
    }
}
