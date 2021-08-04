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

    Login(JFrame homescreen){

        Staff.loadAccounts();

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

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void login(JFrame homescreen) {
        if (Staff.checkAccount(txtUsername.getText(), txtPass.getText())) {
//            Placeholder placeholder = new Placeholder(); //Needs changing
//            placeholder.setVisible(true); //Needs changing
            homescreen.dispose();
            dispose();
            //Cars.loadCarsAdmin(); //Test method
        }
        else {
            JOptionPane.showMessageDialog(null, "Staff login incorrect. Please try again.");
        }
    }
}

