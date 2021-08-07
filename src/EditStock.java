import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        setPreferredSize(new Dimension(450, 450));
        pack();

        fillTextFields(carReg);
//        if (car != null) {
//            txtPrice.setText(car.getPrice().toString());
//            if (car.getAvailableDate() != null) {
//                txtDate.setText(car.getAvailableDate().toString());
//            }
//        }

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editCar(carReg, lstStock);
            }
        });
    }

    public void fillTextFields(String carReg) {

        car = StockController.getCarDetails(carReg);
        if (car.length > 0) {
            txtPrice.setText(car[3]);
            txtDate.setText(car[4]);
        }
        else {
            JOptionPane.showMessageDialog(null, "Error trying to populate text fields.");
        }
    }

    public void editCar(String carReg, JList<String> lstStock) {
        if (inputHandling()) {
            StockController.editStock(carReg, txtPrice.getText().trim(), txtDate.getText().trim());
            StockController.saveStock();
            StockController.populateStockGUI(lstStock, true);
        }
    }

    public boolean inputHandling() {
        if (!(txtPrice.getText().isBlank())) {
            try {
                Float.parseFloat(txtPrice.getText().trim());

                if (car[0].equalsIgnoreCase("true") && txtDate.getText().isBlank()) {
                    return true;
                }
                else if (car[0].equalsIgnoreCase("false") && !(txtDate.getText().isBlank())) {
                    try {
                        LocalDate.parse(txtDate.getText().trim());
                        return true;
                    }
                    catch (DateTimeParseException e) {
                        JOptionPane.showMessageDialog(null, "Please format date as yyyy-MM-dd");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Mismatch between availability and date");
                }
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid price");
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Please enter a valid price");
        }
        return false;

//        if (car[0].equalsIgnoreCase("true")) {
//            if (!(txtPrice.getText().trim().isBlank()) && txtDate.getText().isBlank()) {
//                try {
//                    Float.parseFloat(txtPrice.getText());
//                    return true;
//                }
//                catch (NumberFormatException e) {
//
//                }
//            }
//        }
//        else if (car[0].equalsIgnoreCase("false")) {
//            if (!(txtPrice.getText().trim().isBlank())) {
//                if (!(txtDate.getText().isBlank())) {
//                    try {
//                        LocalDate.parse(txtDate.getText());
//
//                    }
//                    catch (DateTimeParseException e) {
//
//                    }
//                }
//        }
    }

}
