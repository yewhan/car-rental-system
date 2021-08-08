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

    public HomeScreen() {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(350, 250));
        pack();
        frameMain = this;

        btnCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCarHire();
            }
        });

        btnStaff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLogin();
            }
        });
    }

    public static void main(String[] args) {
        HomeScreen homescreen = new HomeScreen();
        homescreen.setVisible(true);
    }

    private void openLogin() {
        Login admin = new Login(frameMain);
        this.setVisible(false);
        admin.setVisible(true);
    }

    private void openCarHire() {
        CarHire carHire = new CarHire();
        carHire.setVisible(true);
        dispose();
    }
}
