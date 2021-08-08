import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDetails extends JFrame {
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

    public CustomerDetails(JFrame customer, String totalCost, String carReg, String returnDate) {
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
        String license = txtLicense.getText().toUpperCase().trim();
        String name = txtName.getText().toUpperCase().trim().replaceAll("[^a-zA-Z\\s]", "");
        String address = txtAddress.getText().toUpperCase().trim();

        if (((name.split("\\s"))).length > 1) {

            if (AccountsController.checkIfCustomerExists(license)) {

                if (!(AccountsController.editCustomer(name, address, license))) {
                    JOptionPane.showMessageDialog(null, "There was an error while updating your details");
                    return;
                }

                if (AccountsController.checkIfCustomerHasCar(license) != null) {

                    JOptionPane.showMessageDialog(null, "Our records indicate you already have a car rented out with us.");
                    return;
                }
            } else {
                AccountsController.addCustomer(name, address, license);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please ensure you've correctly filled out your name");
            return;
        }
        AccountsController.saveCustomer();
        Checkout checkout = new Checkout(frameMain, license, totalCost, carReg, returnDate);
        this.setVisible(false);
        checkout.setVisible(true);
    }
}
