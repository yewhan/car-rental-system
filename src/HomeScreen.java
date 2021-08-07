import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HomeScreen extends JFrame {
    private JPanel panelMain;
    private JLabel lblChoice;
    private JButton btnCustomer;
    private JButton btnStaff;
    private JFrame frameMain;

    public static void main(String[] args) {
        HomeScreen homescreen = new HomeScreen();
        homescreen.setVisible(true);
    }

    private HomeScreen() {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(350, 250));
        pack();
        frameMain = this;

        btnCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCustomer();
            }
        });

        btnStaff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLogin();
            }
        });
    }

    private void openLogin() {
        Login admin = new Login(frameMain);
        this.setVisible(false);
        admin.setVisible(true);
    }

    private void openCustomer() {
        Customer customer = new Customer();
        customer.setVisible(true);
        dispose();
    }
}
