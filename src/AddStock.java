import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCar(lstStock);
            }
        });
    }



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
}

