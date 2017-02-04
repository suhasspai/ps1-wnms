package application;
import static javax.swing.SwingConstants.CENTER;
import static SwingUtilities.SwingConsole.run2;

import SwingUtilities.PopupBox;
import database.DatabaseHandler;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.application.Platform;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author WSN
 */
public class MainApp extends JFrame {
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public MainApp(String title) {
        super(title);
        GBL=new GridBagLayout();
        GBC=new GridBagConstraints();
        GBC.fill=GridBagConstraints.BOTH;
        GBC.gridwidth=GridBagConstraints.REMAINDER;
        GBC.anchor=GridBagConstraints.WEST;
        GBC.weightx=1;
        nodeLabel=new JLabel("Enter Node No.");
        GBC.gridwidth=1;
        GBL.setConstraints(nodeLabel, GBC);
        nodeID=new JComboBox<>();
        String[] nodes={"300", "401", "501"};
        for (String node : nodes)
            nodeID.addItem(node);
        GBL.setConstraints(nodeID, GBC);
        searchNode=new JButton("Search");
        searchNode.setHorizontalAlignment(CENTER);
        GBC.gridwidth=GridBagConstraints.REMAINDER;
        GBL.setConstraints(searchNode, GBC);
        searchNode.addActionListener((ActionEvent e)->{
            Integer nodeNo=toDecimal(nodeID.getSelectedItem().toString());
            DatabaseHandler.query(nodeNo);
        });
        map=new JavaFXMap();
        GBC.gridwidth=3;
        GBC.gridheight=GridBagConstraints.VERTICAL;
        GBL.setConstraints(map, GBC);
        
        setLayout(GBL);
        
        add(nodeLabel);
        add(nodeID);
        add(searchNode);
        add(map);
        Platform.runLater(()->{
            map.createScene();
        });
        
    }
    
    private final JavaFXMap map;
    private final JComboBox<String> nodeID;
    private final JLabel nodeLabel;
    private final JButton searchNode;
    private final GridBagLayout GBL;
    private final GridBagConstraints GBC;
    private Integer toDecimal(String hex) {
        Integer dec=0, size=hex.length();
        for (int i=0; i<size; i++) {
            dec*=16;
            switch (hex.charAt(i)) {
                case '0':
                    break;
                case '1':
                    dec+=1;
                    break;
                case '2':
                    dec+=2;
                    break;
                case '3':
                    dec+=3;
                    break;
                case '4':
                    dec+=4;
                    break;
                case '5':
                    dec+=5;
                    break;
                case '6':
                    dec+=6;
                    break;
                case '7':
                    dec+=7;
                    break;
                case '8':
                    dec+=8;
                    break;
                case '9':
                    dec+=9;
                    break;
                case 'A':
                case 'a':
                    dec+=10;
                    break;
                case 'b':
                case 'B':
                    dec+=11;
                    break;
                case 'c':
                case 'C':
                    dec+=12;
                    break;
                case 'd':
                case 'D':
                    dec+=13;
                    break;
                case 'e':
                case 'E':
                    dec+=14;
                    break;
                case 'f':
                case 'F':
                    dec+=15;
                    break;
            }
        }
        return dec;
    }
}
