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
    private JTextField txtAmount;
    private JPanel panelStock;
    private JPanel panelAdmin;
    private JButton btnEdit;
    private JLabel lblAdmin;
    private JLabel lbCarReg;
    private JButton btnAdd;
    private JButton btnRemove;

    public Admin() {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(750, 500));
        pack();
        StockController.loadCarsAdmin();
        StockController.populateStockGUI(lstStock, true);
        //Stock.overdueAlert(); // TODO: alert if car return is overdue

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

    public void openEditWindow() {
        Stock carToEdit;
        carToEdit = StockController.checkRegistration(txtCarReg.getText());
        if (carToEdit != null) {
            AddEditStock editStock = new AddEditStock(carToEdit, lstStock);
            editStock.setVisible(true);
        }
        else {
            JOptionPane.showMessageDialog(null, "Registration not found. Please try again.");
        }
    }

    public void openAddWindow() {
        AddEditStock addStock = new AddEditStock(lstStock);
        addStock.setVisible(true);
    }

    public void remove() {
        StockController.removeStock(txtCarReg.getText());
        StockController.saveStock();
        StockController.populateStockGUI(lstStock, true);
    }
}
