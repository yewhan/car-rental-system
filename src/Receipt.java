import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.*;

public class Receipt extends JFrame {
    private JTextPane txtReceipt;
    private JPanel panelMain;

    public Receipt(String license, String carReg) {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        pack();

        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setAlignment(attributes, StyleConstants.ALIGN_CENTER);
        txtReceipt.setParagraphAttributes(attributes, true);


        System.out.println("Thread working on GUI: " + Thread.currentThread().getName());
        Threading thread = new Threading(txtReceipt, license, carReg);
    }
}
