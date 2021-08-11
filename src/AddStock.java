import javax.swing.*;
import java.awt.*;

public class AddStock extends JFrame {
    private JPanel panelMain;
    private JTextField txtModel;
    private JTextField txtReg;
    private JTextField txtPrice;
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

        btnBack.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> addCar(lstStock));
    }

    public void addCar(JList<String> lstStock) {
        if (txtModel.getText().isBlank() || txtReg.getText().isBlank() || txtPrice.getText().isBlank()) { //Check to make sure all the fields are correctly filled out
            JOptionPane.showMessageDialog(null, "Please ensure all fields are filled out.");
        } else {
            HomeScreen.stock.addStock(txtModel.getText().toUpperCase(), txtReg.getText().toUpperCase(),
                    txtPrice.getText().toUpperCase());
            HomeScreen.stock.saveStock();
            HomeScreen.stock.populateStockGUI(lstStock, true); //add the car to the database, reflect changes in GUI then close window
            dispose();
        }
    }
}

