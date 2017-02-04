package SwingUtilities;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Suhas S Pai
 */

public class SwingConsole {
    public static void run(final JFrame f, final int width, final int height) {
	SwingUtilities.invokeLater(()->{
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(width, height);
            f.setVisible(true);
            f.setMinimumSize(new Dimension(width/2, height/2));
        });
    }
    public static void run2(final JFrame f, final int width, final int height) {
        SwingUtilities.invokeLater(()->{
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setSize(width, height);
            f.setVisible(true);
            f.setMinimumSize(new Dimension(width/2, height/2));
        });
    }
}