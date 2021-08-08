import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
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
    private final JFrame frameMain;

    public CarHire() {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(750, 500));
        pack();
        btnCheckout.setEnabled(false); //disable checkout button until conditions are met
        StockController.loadCars(); //load a list of cars to be displayed
        StockController.populateStockGUI(lstCars, false); //update display with only information that customers should see, of available cars
        frameMain = this; //save window, so it can be returned to via back button later

        btnBack.addActionListener(e -> { //return to homescreen
            StockController.clearCarList();
            dispose();
            HomeScreen homescreen = new HomeScreen();
            homescreen.setVisible(true);
        });

        btnCheckout.addActionListener(e -> openCustomerDetails());

        btnReturnCar.addActionListener(e -> returnCar());

        txtCarReg.getDocument().addDocumentListener(new DocumentListener() { //when txt box is updated check the inputs
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

        txtDate.getDocument().addDocumentListener(new DocumentListener() { //when txt box is updated check the inputs
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
                if (txtDate.getText().equals("yyyy-MM-dd")) { //when clicked on, if text box contains date format, clear it
                    txtDate.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtDate.getText().isEmpty()) { //when clicked off of, if text box is empty, fill it with date format
                    txtDate.setText("yyyy-MM-dd");
                }
            }
        });
    }

    public void checkInputs() {
        String carReg = txtCarReg.getText().trim();
        String date = txtDate.getText().trim();

        if (!(date.isBlank()) || !(carReg.isBlank())) { //check to see if either text box is empty
            if (StockController.checkReg(carReg)) { //check to see if the txtCarReg matches one on database
                if (StockController.checkAvailability(carReg)) { //check to see if car is available
                    try {
                        LocalDate $date = LocalDate.parse(date); //check to see if txtDate is valid format
                        long difference = ChronoUnit.DAYS.between(LocalDate.now(), $date);
                        if (difference > 0) { //make sure the date entered is after today's date
                            lblTotalPrice.setText(StockController.calculateTotalPrice(carReg, $date)); //display the total cost for hiring the car
                            btnCheckout.setEnabled(true); //allow the user to enter checkout
                            return;
                        }
                    } catch (DateTimeParseException ignored) {

                    }
                }
            }
        }
        btnCheckout.setEnabled(false); //if any issues arise, ensure checkout button is disabled and lblTotalPrice is empty
        lblTotalPrice.setText("");
    }

    public void openCustomerDetails() {
        CustomerDetails customerDetails = new CustomerDetails(frameMain, lblTotalPrice.getText(),
                txtCarReg.getText().trim(), txtDate.getText().trim());
        customerDetails.setVisible(true);
        this.setVisible(false);
    }

    public void returnCar() {
        ReturnCar returnCar = new ReturnCar(lstCars);
        returnCar.setVisible(true);
    }
}
