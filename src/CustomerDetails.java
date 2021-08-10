import javax.swing.*;
import java.awt.*;

public class CustomerDetails extends JFrame {
    private final JFrame frameMain;
    private JPanel panelMain;
    private JTextField txtName;
    private JTextField txtAddress;
    private JTextField txtLicense;
    private JButton btnBack;
    private JButton btnCheckout;
    private JLabel lblName;
    private JLabel lblAddress;
    private JLabel lblLicense;

    public CustomerDetails(JFrame customer, String totalCost, String carReg, String returnDate) {
        CustomerController.loadCustomers();
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(450, 450));
        pack();
        frameMain = this;

        btnBack.addActionListener(e -> {
            dispose();
            customer.setVisible(true);
        });

        btnCheckout.addActionListener(e -> {
            if (checkDetails()) {
                openCheckout(totalCost, carReg, returnDate);
            }
        });
    }

    public boolean checkDetails() {
        if (txtName.getText().isBlank() || txtAddress.getText().isBlank() || txtLicense.getText().isBlank()) { //if any of the inputs are empty, inform user
            JOptionPane.showMessageDialog(null, "Please ensure all fields are correctly filled out");
            return false;
        }
        return true;
    }

    public void openCheckout(String totalCost, String carReg, String returnDate) {
        String license = txtLicense.getText().toUpperCase().trim();
        String name = txtName.getText().toUpperCase().trim().replaceAll("[^a-zA-Z\\s]", ""); //remove all non-alphabetic/ whitespace characters from string
        String address = txtAddress.getText().toUpperCase().trim();

        if (((name.split("\\s"))).length > 1) { //ensure at least first + last name is entered

            if (CustomerController.checkIfCustomerExists(license)) { //check if customer is already in database

                if (!(CustomerController.editCustomer(name, address, license))) { //attempt to update customer's name and address with inputs
                    JOptionPane.showMessageDialog(null, "There was an error while updating your details");
                    return;
                }

                if (CustomerController.checkIfCustomerHasCar(license) != null) { //if customer already has car rented inform them

                    JOptionPane.showMessageDialog(null, "Our records indicate you already have a car rented out with us.");
                    return;
                }
            } else { //if customer doesn't exist, add add them
                CustomerController.addCustomer(name, address, license);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please ensure you've correctly filled out your name");
            return;
        }
        CustomerController.saveCustomer(); //save new customer/ details
        Checkout checkout = new Checkout(frameMain, license, totalCost, carReg, returnDate);
        this.setVisible(false);
        checkout.setVisible(true);
    }
}
