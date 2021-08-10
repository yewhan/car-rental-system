import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private final StaffController staff = new StaffController();
    private JPanel panelMain;
    private JTextField txtPass;
    private JTextField txtUsername;
    private JButton btnBack;
    private JButton btnLogin;
    private JLabel lblPass;
    private JLabel lblUsername;

    Login(JFrame homescreen) {

        staff.loadStaff(); //Initialising list of staff login details to check against

        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(350, 250));
        pack();

        btnLogin.addActionListener(e -> login(homescreen)); //using lambda instead of ActionListeners to improve readability

        btnBack.addActionListener(e -> { //return to homescreen
            dispose();
            homescreen.setVisible(true);
        });
    }

    public void login(JFrame homescreen) {
        if (staff.checkLogin(txtUsername.getText(), staff.hash(txtPass.getText().trim()))) { //if entered details match account, log in
            Admin admin = new Admin();
            admin.setVisible(true);
            homescreen.dispose();
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Account credentials incorrect. Please try again."); //else inform user of incorrect details
        }
    }
}

