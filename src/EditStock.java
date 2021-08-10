import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EditStock extends JFrame {
    private JPanel panelMain;
    private JTextField txtPrice;
    private JTextField txtDate;
    private JButton btnBack;
    private JButton btnSave;
    private JLabel lblPrice;
    private JLabel lblDate;
    private String[] car;

    public EditStock(String carReg, JList<String> lstStock) {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(350, 350));
        pack();

        fillTextFields(carReg);

        btnBack.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> editCar(carReg, lstStock));
    }

    public void fillTextFields(String carReg) {
        car = CarHire.stock.getCarDetails(carReg); //fill string array with car details
        if (car.length > 0) { //if the string array has been filled out, populate text fields with car's original details
            txtPrice.setText(car[3]);
            txtDate.setText(car[4]);
        } else {
            JOptionPane.showMessageDialog(null, "Error trying to populate text fields.");
        }
    }

    public void editCar(String carReg, JList<String> lstStock) {
        if (inputHandling()) { //check to see if all inputs are valid
            if (CarHire.stock.editStock(carReg, txtPrice.getText().toUpperCase().trim(), txtDate.getText().toUpperCase().trim())) { //pass inputs from txtboxes and designated car's reg
                CarHire.stock.saveStock(); //save changes to database
                CarHire.stock.populateStockGUI(lstStock, true); //reflect changes in GUI
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(null, "Error trying to update car details");
            }
        }
    }

    public boolean inputHandling() {
        String price = txtPrice.getText().trim();
        String date = txtDate.getText().trim();

        if (!(price.isBlank())) { //check if txtPrice is empty
            try {
                Float.parseFloat(price); //check to see if txtPrice is a valid float

                if (car[0].equalsIgnoreCase("true") && date.isBlank()) { //if car being edited is available return true
                    return true;
                } else if (car[0].equalsIgnoreCase("false") && !(date.isBlank())) { //if car is unavailable, make sure txtDate has an entry
                    try {
                        LocalDate.parse(date); //if txtDate is valid date, return true
                        return true;
                    } catch (DateTimeParseException e) {
                        JOptionPane.showMessageDialog(null, "Please format date as yyyy-MM-dd");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Mismatch between availability and date");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid price");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid price");
        }
        return false;
    }

}
