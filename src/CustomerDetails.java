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
    private JFrame frameMain;

    public Checkout(JFrame customer, String totalCost, String carReg, String returnDate) {
        AccountsController.loadCustomers();
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(450, 450));
        pack();
        frameMain = this;

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
                if (checkDetails()) {
                    openCheckout(totalCost, carReg, returnDate);
                }
            }
        });
    }

    public boolean checkDetails() {
        if (txtName.getText().isBlank() || txtAddress.getText().isBlank() || txtLicense.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Please ensure all fields are correctly filled out");
            return false;
        }
        return true;
    }

    public void openCheckout(String totalCost, String carReg, String returnDate) {
        if (AccountsController.checkIfCustomerExists(txtLicense.getText())) {
            if (AccountsController.checkIfCustomerHasCar(txtLicense.getText())) {
                JOptionPane.showMessageDialog(null, "Our records indicate you already have a car rented out with us.");
                return;
            }
        }
        else {
            AccountsController.addCustomer(txtName.getText(), txtAddress.getText(), txtLicense.getText());
        }
        Payment payment = new Payment(frameMain, txtLicense.getText(), totalCost, carReg, returnDate);
        this.setVisible(false);
        payment.setVisible(true);
    }
}
