package application;

import static SwingUtilities.SwingConsole.run;
import static serial.ReadFromPort.read;

import javafx.scene.web.WebEngine;

/**
 *
 * @author Suhas S Pai
 */

public class SwingView {
    public static void main(String[] args) {
        run(new MainApp("DAE WiRaM"), 370, 820);
        read();
    }
    public static void changeIcon(boolean value, String nodeNo) {
        WebEngine engine=JavaFXMap.getWebEngine();
        if (value) {
            engine.executeScript("for (var node in nodes) {"
                                    + "if (node.id==" + nodeNo + ")"
                                        + "node.setIcon(alarmInactive);"
                                + "}");
        } else {
            engine.executeScript("for (var node in nodes) {"
                                    + "if (node.id==" + nodeNo + ")"
                                        + "node.setIcon(noAlarmInactive);"
                                + "}");
        }
    }
}
