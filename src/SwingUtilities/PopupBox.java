package SwingUtilities;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Suhas S Pai
 */

public class PopupBox extends JFrame {
    public PopupBox(String text) {
        super("Warning");
        setLayout(new FlowLayout());
        add(new JLabel(text));
        JButton closeButton=new JButton("Close");
        closeButton.addActionListener((ActionEvent e)->{
            dispose();
        });
        add(closeButton);
    }
}