import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Customer extends JFrame {
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

    public Customer() {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(750, 500));
        pack();
        StockController.loadCarsCustomer();
        StockController.populateStockGUI(lstCars, false);

        btnCheckout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
