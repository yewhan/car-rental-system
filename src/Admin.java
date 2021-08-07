import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public Admin() {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 500));
        pack();
        AccountsController.loadCustomers();
        StockController.loadCars();
        StockController.populateStockGUI(lstStock, true);
        //Stock.overdueAlert(); // TODO: alert if car return is overdue

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openHomescreen();
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEditWindow();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddWindow();
            }
        });

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove();
            }
        });
    }

    public void openHomescreen() {
        StockController.clearCarList();
        dispose();
        HomeScreen homescreen = new HomeScreen();
        homescreen.setVisible(true);
    }

    public void openEditWindow() {
        if (StockController.checkReg(txtCarReg.getText().trim())) {
            EditStock editStock = new EditStock(txtCarReg.getText().trim(), lstStock);
            editStock.setVisible(true);
        }
        else {
            JOptionPane.showMessageDialog(null, "Registration not found. Please try again.");
        }
//        Stock carToEdit;
//        carToEdit = StockController.returnCar(txtCarReg.getText());
//        if (carToEdit != null) {
//            EditStock editStock = new EditStock(carToEdit, lstStock);
//            editStock.setVisible(true);
//        }
//        else {
//            JOptionPane.showMessageDialog(null, "Registration not found. Please try again.");
//        }
    }

    public void openAddWindow() {
        AddStock addStock = new AddStock(lstStock);
        addStock.setVisible(true);
    }

    public void remove() {
        StockController.removeStock(txtCarReg.getText());
        StockController.saveStock();
        StockController.populateStockGUI(lstStock, true);
    }
}
