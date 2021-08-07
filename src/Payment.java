import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Payment extends JFrame {

    private JPanel panelMain;
    private JLabel lblBalance;
    private JLabel lblPrice;
    private JButton btnBack;
    private JButton btnPay;
    private JFrame frameMain;

    public Payment(JFrame customerDetails, String license, String totalCost, String carReg, String returnDate) {
        lblPrice.setText(totalCost);
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(475, 200));
        pack();
        frameMain = this;

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                customerDetails.setVisible(true);
            }
        });

        btnPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
