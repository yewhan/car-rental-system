import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Checkout extends JFrame {
    private JPanel panelMain;
    private JTextField txtName;
    private JTextField txtAddress;
    private JTextField txtLicense;
    private JButton btnBack;
    private JButton btnCheckout;
    private JLabel lblName;
    private JLabel lblAddress;
    private JLabel lblLicense;

    public Checkout(JFrame customer, String totalCost, String carReg, String returnDate) {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(450, 450));
        pack();

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                customer.setVisible(true);
            }
        });

        btnCheckout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
