import javax.swing.*;
import java.awt.*;

public class Receipt extends JFrame {
    private JTextArea txtReceipt;
    private JPanel panelMain;

    public Receipt(String license, String carReg) {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        pack();

        txtReceipt.setLineWrap(true);

        System.out.println("Thread working on GUI: " + Thread.currentThread().getName());
        Threading thread = new Threading(txtReceipt, license, carReg);
    }
}
