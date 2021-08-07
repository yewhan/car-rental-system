import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AddStock extends JFrame {
    private JPanel panelMain;
    //private JTextField txtAvailable;
    private JTextField txtModel;
    private JTextField txtReg;
    private JTextField txtPrice;
    //private JTextField txtAvailableDate;
    private JButton btnBack;
    private JButton btnSave;
    private JLabel lblModel;
    private JLabel lblReg;
    private JLabel lblPrice;

    public AddStock(JList<String> lstStock) {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(450, 450));
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
//                if (car == null) {
//                    addCar();
//                }
//                else {
//                    editCar(car);
//                }
                addCar(lstStock);
            }
        });
//        AddEditStockLogic(null, lstStock);
    }

//    public AddStock(Stock car, JList lstStock) {
//        if (car != null) {
//            txtAvailable.setText(car.getAvailable().toString());
//            txtModel.setText(car.getModel());
//            txtReg.setText(car.getRegistration());
//            txtPrice.setText(car.getPrice().toString());
//            if (car.getAvailableDate() != null) {
//                txtAvailableDate.setText(car.getAvailableDate().toString());
//            }
//            AddEditStockLogic(car, lstStock);
//        }
//    }

//    public void AddEditStockLogic(Stock car, JList lstStock) {
//        setContentPane(panelMain);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setPreferredSize(new Dimension(450, 450));
//        pack();
//
//        btnBack.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                dispose();
//            }
//        });
//
//        btnSave.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                if (car == null) {
////                    addCar();
////                }
////                else {
////                    editCar(car);
////                }
//                addCar();
//                StockController.populateStockGUI(lstStock, true);
//            }
//        });
//    }

    public void addCar(JList<String> lstStock) {
        if (txtModel.getText().isBlank() || txtReg.getText().isBlank() || txtPrice.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Please ensure all fields are filled out.");
        }
        else {
            StockController.addStock(txtModel.getText(), txtReg.getText(), txtPrice.getText());
            StockController.saveStock();
            StockController.populateStockGUI(lstStock, true);
            dispose();
        }
    }

//    public void editCar(Stock car) {
//        if(exceptionHandling()) {
//
//            StockController.editStock(car, txtAvailable.getText(), txtModel.getText(), txtReg.getText(), txtPrice.getText(), txtAvailableDate.getText());
//            StockController.saveStock();
//            dispose();
//        }
//    }


//    public boolean exceptionHandling() {
//        if (txtAvailable.getText().equals("false") || txtAvailable.getText().equals("true")) {
//            try {
//                Float $temp = Float.parseFloat(txtPrice.getText());
//
//                if (txtAvailable.getText().equals("false")) {
//                    try {
//                        LocalDate $tempDate = LocalDate.parse(txtAvailableDate.getText());
//                    }
//                    catch (DateTimeParseException e) {
//                        JOptionPane.showMessageDialog(null, "Car next available date incompatible. Please try again.");
//                        return false;
//                    }
//                }
//            }
//            catch (NumberFormatException e){
//                JOptionPane.showMessageDialog(null, "Car price incompatible. Please try again.");
//                return false;
//            }
//        }
//        else {
//            JOptionPane.showMessageDialog(null, "Car availability incompatible. Please try again.");
//            return false;
//        }
//        return true;
//    }
}

