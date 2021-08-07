import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPanel panelMain;
    private JTextField txtPass;
    private JTextField txtUsername;
    private JButton btnBack;
    private JButton btnLogin;
    private JLabel lblPass;
    private JLabel lblUsername;

    Login(JFrame homescreen) {

        AccountsController.loadStaff();

        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(350, 250));
        pack();

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(homescreen);
            }
        });

//        btnBack.addActionListener(e -> dispose()); //lambda - can be used for all actionlisteners
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                homescreen.setVisible(true);
            }
        });
    }

    public void login(JFrame homescreen) {
        if (AccountsController.checkLogin(txtUsername.getText(), txtPass.getText())) {
            Admin admin = new Admin();
            admin.setVisible(true);
            homescreen.dispose();
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Account credentials incorrect. Please try again.");
        }
    }
}

