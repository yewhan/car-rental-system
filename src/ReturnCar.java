import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnCar extends JFrame {

    private JTextField txtLicense;
    private JPanel panelMain;
    private JTextField txtCarReg;
    private JButton btnBack;
    private JButton btnSave;
    private JLabel lblLicense;
    private JLabel lblCarReg;

    public ReturnCar(JList<String> lstCars) {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(350, 350));
        pack();

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnCar(lstCars);
            }
        });
    }

    public void returnCar(JList<String> lstCars) {
        String carReg = txtCarReg.getText().trim();
        String license = txtLicense.getText().trim();

        if (!(carReg.isEmpty()) && !(license.isEmpty())) {
            if (StockController.checkReg(carReg)) {
                if (!(StockController.checkAvailability(carReg))) {
                    AccountsController.loadCustomers();
                    if (AccountsController.checkIfCustomerExists(license)) {
                        String $carReg = AccountsController.checkIfCustomerHasCar(license);
                        if ($carReg != null && $carReg.equalsIgnoreCase(carReg)) {
                            StockController.setCarToAvailable(carReg);
//                            StockController.loadCars();
                            StockController.saveStock();
                            StockController.populateStockGUI(lstCars, true);
                            AccountsController.removeCarFromCustomer(license, carReg);
//                            AccountsController.loadCustomers();
                            AccountsController.saveCustomer();
                            AccountsController.clearCustomerList();
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "This car has not been rented by this user");
                            return;
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Driving License Number is not on our system");
                        return;
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Car is already available");
                    return;
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Car does not exist");
                return;
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Please make sure both text fields are filled out");
            return;
        }
    }
}
