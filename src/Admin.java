import javax.swing.*;
import java.awt.*;

public class Admin extends JFrame {
    private JPanel panelMain;
    private JLabel lblMain;
    private JList<String> lstStock;
    private JLabel lblStock;
    private JTextField txtCarReg;
    private JPanel panelStock;
    private JPanel panelAdmin;
    private JButton btnEdit;
    private JLabel lblAdmin;
    private JLabel lbCarReg;
    private JButton btnAdd;
    private JButton btnRemove;
    private JButton btnBack;

//    private AdminController admin = new Admin();

    public Admin() {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 500));
        pack();
        CustomerController.loadCustomers(); //Initialise list of customers to display who has rented what cars
        CarHire.stock.loadCars(); //Initialise list of cars to display
        CarHire.stock.populateStockGUI(lstStock, true); //pass in JList, so it can be worked on, pass in true to tell method to display information meant for staff
        //Stock.overdueAlert(); // TODO: alert if car return is overdue

        btnBack.addActionListener(e -> openHomescreen());

        btnEdit.addActionListener(e -> openEditWindow());

        btnAdd.addActionListener(e -> openAddWindow());

        btnRemove.addActionListener(e -> remove());
    }

    public void openHomescreen() { //return to homescreen
        dispose();
        HomeScreen homescreen = new HomeScreen();
        homescreen.setVisible(true);
    }

    public void openEditWindow() {
        if (CarHire.stock.checkReg(txtCarReg.getText().trim())) { //if input registration plate matches a car in our system, open edit stock window
            EditStock editStock = new EditStock(txtCarReg.getText().trim(), lstStock);
            editStock.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Registration not found. Please try again.");
        }
    }

    public void openAddWindow() {
        AddStock addStock = new AddStock(lstStock);
        addStock.setVisible(true);
    }

    public void remove() { //Remove car from system, then remove it from database and finally reflect changes on GUI
        CarHire.stock.removeStock(txtCarReg.getText());
        CarHire.stock.saveStock();
        CarHire.stock.populateStockGUI(lstStock, true);
    }
}
