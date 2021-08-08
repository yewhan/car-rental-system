import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class CarHire extends JFrame {
    private JPanel panelMain;
    private JList<String> lstCars;
    private JTextField txtCarReg;
    private JTextField txtDate;
    private JButton btnCheckout;
    private JLabel lblTitle;
    private JLabel lblCarReg;
    private JLabel lblDate;
    private JLabel lblPrice;
    private JLabel lblTotalPrice;
    private JButton btnBack;
    private JButton btnReturnCar;
    private JFrame frameMain;

    public CarHire() {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(750, 500));
        pack();
        btnCheckout.setEnabled(false);
        StockController.loadCars();
        StockController.populateStockGUI(lstCars, false);
        frameMain = this;

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockController.clearCarList();
                dispose();
                HomeScreen homescreen = new HomeScreen();
                homescreen.setVisible(true);
            }
        });

        btnCheckout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCheckout();
            }
        });

        btnReturnCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnCar();
            }
        });

        txtCarReg.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkInputs();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkInputs();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkInputs();
            }
        });

        txtDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtDate.getText().equals("yyyy-MM-dd")) {
                    txtDate.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtDate.getText().isEmpty()) {
                    txtDate.setText("yyyy-MM-dd");
                }
            }
        });

        txtDate.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkInputs();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkInputs();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkInputs();
            }
        });
    }

    public void checkInputs() {
        if (!(txtDate.getText().isBlank()) && !(txtCarReg.getText().isBlank())) {
            if (StockController.checkReg(txtCarReg.getText().trim())) {
                if (StockController.checkAvailability(txtCarReg.getText().trim())) {
                    try {
                        LocalDate date = LocalDate.parse(txtDate.getText().trim());
                        long difference = ChronoUnit.DAYS.between(LocalDate.now(), date);
                        if (difference > 0) {
                            lblTotalPrice.setText(StockController.calculateTotalPrice(txtCarReg.getText().trim(), date));
                            btnCheckout.setEnabled(true);
                            return;
                        }
                    } catch (DateTimeParseException ignored) {

                    }
                }
            }
        }
        btnCheckout.setEnabled(false);
        lblTotalPrice.setText("");
//        if (!(txtDate.getText().isBlank()) && !(txtCarReg.getText().isBlank())) {
//            try {
//                LocalDate date = LocalDate.parse(txtDate.getText().trim());
//                lblTotalPrice.setText(StockController.calculateTotalPrice(txtCarReg.getText().trim(), date));
//            } catch (DateTimeParseException e) {
//                lblTotalPrice.setText("");
////                btnCheckout.setEnabled(false);
//            }
//        } else {
//            lblTotalPrice.setText("");
////            btnCheckout.setEnabled(false);
//        }
    }

    public void openCheckout() {
//        if (!(lblTotalPrice.getText().isBlank())) {
            CustomerDetails customerDetails = new CustomerDetails(frameMain, lblTotalPrice.getText(),
                    txtCarReg.getText().trim(), txtDate.getText().trim());
            customerDetails.setVisible(true);
            this.setVisible(false);
//        }
    }

    public void returnCar() {
        ReturnCar returnCar = new ReturnCar(lstCars);
        returnCar.setVisible(true);
    }
}
