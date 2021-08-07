import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
    private JFrame frameMain;

    public CarHire() {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(750, 500));
        pack();
        StockController.loadCarsCustomer();
        StockController.populateStockGUI(lstCars, false);
        frameMain = this;

        btnCheckout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCheckout();
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
            try {
                LocalDate date = LocalDate.parse(txtDate.getText());
                lblTotalPrice.setText(StockController.calculateTotalPrice(txtCarReg.getText(), date));
            }
            catch (DateTimeParseException e) {
                lblTotalPrice.setText("");
            }
        }
        else {
            lblTotalPrice.setText("");
        }
    }

    public void openCheckout() {
        if (!(lblTotalPrice.getText().isBlank())) {
            CustomerDetails customerDetails = new CustomerDetails(frameMain, lblTotalPrice.getText(), txtCarReg.getText(), txtDate.getText());
            customerDetails.setVisible(true);
            this.setVisible(false);
        }
    }
}
