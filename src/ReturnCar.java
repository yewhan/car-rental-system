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

        if (!(carReg.isEmpty()) && !(license.isEmpty())) { //check to see if inputs have been filled out
            if (CarHire.stock.checkReg(carReg)) { //check to see if car reg matches one in database
                if (!(CarHire.stock.checkAvailability(carReg))) { //make sure car is unavailable
                    AccountsController.loadCustomers(); //load a list of customers to check against
                    if (AccountsController.checkIfCustomerExists(license)) { //make sure customer entry exists in database
                        String $carReg = AccountsController.checkIfCustomerHasCar(license); //check if customer has outstanding car, if they do add to $carReg
                        if ($carReg != null && $carReg.equalsIgnoreCase(carReg)) { //if a car reg was returned, and car reg matches input car reg
                            CarHire.stock.setCarToAvailable(carReg); //make car available
                            CarHire.stock.saveStock(); //store car to database
                            CarHire.stock.populateStockGUI(lstCars, false); //reflect changes on customer's GUI
                            AccountsController.removeCarFromCustomer(license, carReg); //remove the outstanding car from customer's account
                            AccountsController.saveCustomer(); //store customer to database
                            AccountsController.clearCustomerList(); //empty list of customers
                            dispose(); //return to CarHire window
                        } else {
                            JOptionPane.showMessageDialog(null, "This car has not been rented by this user");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Driving License Number is not on our system");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Car is already available");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Car does not exist");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please make sure both text fields are filled out");
        }
    }
}
