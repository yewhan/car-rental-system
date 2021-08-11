import javax.swing.*;
import java.awt.*;


public class HomeScreen extends JFrame {
    public static StockController stock = new StockController();
    private final JFrame frameMain;
    private JPanel panelMain;
    private JLabel lblChoice;
    private JButton btnCustomer;
    private JButton btnStaff;

    public HomeScreen() {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(350, 250));
        pack();
        frameMain = this; //initialising JFrame for use when pressing back buttons

        btnCustomer.addActionListener(e -> openCarHire()); //Lambda expressions used instead of ActionListener interface for readability

        btnStaff.addActionListener(e -> openLogin());
    }

    public static void main(String[] args) {
        HomeScreen homescreen = new HomeScreen();
        homescreen.setVisible(true);
    }

    public void openLogin() { //open login screen
        Login admin = new Login(frameMain);
        this.setVisible(false);
        admin.setVisible(true);
    }

    public void openCarHire() { //open customer's GUI
        CarHire carHire = new CarHire();
        carHire.setVisible(true);
        dispose();
    }
}
