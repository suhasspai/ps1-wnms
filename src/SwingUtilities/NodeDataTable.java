package SwingUtilities;

import static java.awt.Font.SANS_SERIF;
import static java.awt.Font.TRUETYPE_FONT;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.SwingConstants.CENTER;
import org.bson.Document;

/**
 *
 * @author Suhas S Pai
 */

public class NodeDataTable extends JFrame {
    public NodeDataTable(Document document) {
        super("Node Data");
        windowLayout=new GridBagLayout();
        constraints=new GridBagConstraints();
        active=false;
        
        setValue(document);
        setAlarm(document);
        setOthers(document);
        setHealthPanel(document);
        constraints.fill=GridBagConstraints.BOTH;
        constraints.anchor=GridBagConstraints.WEST;
        constraints.gridwidth=1;
        windowLayout.setConstraints(valuePanel, constraints);
        windowLayout.setConstraints(alarmPanel, constraints);
        windowLayout.setConstraints(otherPanel, constraints);
        windowLayout.setConstraints(healthPanel, constraints);
        setLayout(windowLayout);
        
        add(valuePanel);
        add(alarmPanel);
        add(otherPanel);
        add(healthPanel);
    }
    public static boolean isWindowActive() {
        return active;
    }
    public static void setWindowActive(boolean active) {
        NodeDataTable.active=active;
        if (active==false)
            nodeNo=-1;
    }
    public static void main(String args[]) {
        SwingConsole.run(new NodeDataTable(
            new Document("GroupID", 3)
            .append("NodeID", 768)
            .append("No_of_sensor_values", 1)
            .append("Radiation (mRad/hr) 4-digit display", 65535)
            .append("Health", new Document("Alarm", Boolean.toString(false))
                .append("Sensor Connected", Boolean.toString(false))
                .append("Sync Bit", Boolean.toString(false))
                .append("Power Supply", Boolean.toString(true)))
            .append("Timestamp", "04:14:50")
        ), 800, 500);
    }
    public static Integer getActiveNodeNo() {
        return nodeNo;
    }
    public static void change(Document document) {
        if (isWindowActive()) {
            valueLabel.setText(Integer.toString(document.getInteger(
                "Radiation (mRad/hr) 4-digit display")
            ));
            String alarm=((Document)document.get("Health")).getString("Alarm");
            Color colour;
            if (alarm.equalsIgnoreCase("false"))
                colour=new Color(0, 255, 0);
            else
                colour=new Color(255, 0, 0);
            alarmPanel.setBackground(colour);
            nodeNo=(Integer)document.get("NodeID");
            nodeValue.setText(toHex(nodeNo));
            groupValue.setText(document.get("GroupID").toString());
            timeValue.setText(document.get("Timestamp").toString());
            String powerSupply=((Document)document.get("Health")).getString("Power Supply");
            powerState=!powerSupply.equalsIgnoreCase("false");
            powerBoxOn.setSelected(powerState);
            powerBoxOff.setSelected(!powerState);
            String syncBit=((Document)document.get("Health")).getString("Sync Bit");
            syncState=!syncBit.equalsIgnoreCase("false");
            syncBoxOn.setSelected(syncState);
            syncBoxOff.setSelected(!syncState);
            String sensorConnected=((Document)document.get("Health")).getString("Sensor Connected");
            sensorState=!sensorConnected.equalsIgnoreCase("false");
            sensorBoxOn.setSelected(sensorState);
            sensorBoxOff.setSelected(!sensorState);
        }
    }
    
    private static JPanel valuePanel, alarmPanel, otherPanel, healthPanel;
    private GridBagLayout valueLayout, alarmLayout, windowLayout, otherLayout, healthLayout;
    private final GridBagConstraints constraints;
    private static JLabel valueText, alarmText, valueLabel, nodeText, nodeValue, groupText,
        groupValue, timeText, timeValue, powerText, syncText, sensorText;
    private static JCheckBox powerBoxOn, powerBoxOff, syncBoxOn, syncBoxOff, sensorBoxOn, sensorBoxOff;
    private JLabel blankText0, blankText1, blankText2, blankText3, blankText4;
    private static boolean active=false, powerState, syncState, sensorState;
    private static Integer nodeNo;
    private void setAlarm(Document document) {
        String alarm=((Document)document.get("Health")).getString("Alarm");
        Color colour;
        if (alarm.equalsIgnoreCase("false"))
            colour=new Color(0, 255, 0);
        else
            colour=new Color(255, 0, 0);
        alarmLayout=new GridBagLayout();
        alarmText=new JLabel("Alarm");
        alarmText.setHorizontalAlignment(CENTER);
        alarmText.setFont(new Font(SANS_SERIF, Font.BOLD, 32));
        alarmLayout.setConstraints(alarmText, constraints);
        alarmPanel=new JPanel(alarmLayout);
        alarmPanel.setBackground(colour);
        alarmPanel.add(alarmText, 0);
    }
    private void setValue(Document document) {
        constraints.fill=GridBagConstraints.BOTH;
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        constraints.weightx=1;
        valueLayout=new GridBagLayout();
        valueText=new JLabel("Value");
        valueText.setFont(new Font(SANS_SERIF, Font.BOLD, 32));
        valueText.setHorizontalAlignment(CENTER);
        valueLayout.setConstraints(valueText, constraints); // value
        valueLabel=new JLabel(Integer.toString(document.getInteger(
            "Radiation (mRad/hr) 4-digit display")
        ));
        valueLabel.setFont(new Font(SANS_SERIF, TRUETYPE_FONT, 32));
        valueLabel.setHorizontalAlignment(CENTER);
        valueLayout.setConstraints(valueLabel, constraints);
        
        blankText1=new JLabel("mRad/hr");
        blankText1.setFont(new Font(SANS_SERIF, TRUETYPE_FONT, 32));
        blankText1.setHorizontalAlignment(CENTER);
        valueLayout.setConstraints(blankText1, constraints);
        
        valuePanel=new JPanel(valueLayout);
        valuePanel.add(valueText);
        valuePanel.add(valueLabel);
        valuePanel.add(blankText1);
    }
    private static String toHex(Integer n) {
        String s="", temp="";
        while (n>0) {
            temp=String.valueOf(n%16);
            s=temp.concat(s);
            n/=16;
        }
        return s;
    }
    private String decodeHexSize(Integer size) {
        switch(size) {
            case 0:
                return "0";
            case 1:
                return "1";
            case 2:
                return "2";
            case 3:
                return "3";
            case 4:
                return "4";
            case 5:
                return "5";
            case 6:
                return "6";
            case 7:
                return "7";
            case 8:
                return "8";
            case 9:
                return "9";
            case 10:
                return "A";
            case 11:
                return "B";
            case 12:
                return "C";
            case 13:
                return "D";
            case 14:
                return "E";
            case 15:
                return "F";
            default:
                return "";
        }
    }
    private void setOthers(Document document) {
        nodeText=new JLabel("Node ID");
        nodeText.setFont(new Font(SANS_SERIF, Font.BOLD, 32));
        nodeText.setHorizontalAlignment(CENTER);
        otherLayout=new GridBagLayout();
        otherLayout.setConstraints(nodeText, constraints);
        nodeNo=(Integer)document.get("NodeID");
        nodeValue=new JLabel(toHex(nodeNo));
        nodeValue.setFont(new Font(SANS_SERIF, TRUETYPE_FONT, 32));
        nodeValue.setHorizontalAlignment(CENTER);
        otherLayout.setConstraints(nodeValue, constraints);
        
        blankText0=new JLabel("   ");
        blankText0.setFont(new Font(SANS_SERIF, TRUETYPE_FONT, 32));
        blankText0.setHorizontalAlignment(CENTER);
        otherLayout.setConstraints(blankText0, constraints);
        
        groupText=new JLabel("Group ID");
        groupText.setFont(new Font(SANS_SERIF, Font.BOLD, 32));
        groupText.setHorizontalAlignment(CENTER);
        otherLayout.setConstraints(groupText, constraints);
        groupValue=new JLabel(document.get("GroupID").toString());
        groupValue.setFont(new Font(SANS_SERIF, TRUETYPE_FONT, 32));
        groupValue.setHorizontalAlignment(CENTER);
        otherLayout.setConstraints(groupValue, constraints);
        
        timeText=new JLabel("Timestamp");
        timeText.setFont(new Font(SANS_SERIF, Font.BOLD, 32));
        timeText.setHorizontalAlignment(CENTER);
        otherLayout.setConstraints(timeText, constraints);
        timeValue=new JLabel(document.get("Timestamp").toString());
        timeValue.setFont(new Font(SANS_SERIF, TRUETYPE_FONT, 32));
        timeValue.setHorizontalAlignment(CENTER);
        otherLayout.setConstraints(timeValue, constraints);
        
        blankText2=new JLabel("     ");
        blankText2.setFont(new Font(SANS_SERIF, TRUETYPE_FONT, 32));
        blankText2.setHorizontalAlignment(CENTER);
        otherLayout.setConstraints(blankText2, constraints);
        
        otherPanel=new JPanel(otherLayout);
        otherPanel.add(nodeText);
        otherPanel.add(nodeValue);
        otherPanel.add(blankText0);
        otherPanel.add(groupText);
        otherPanel.add(groupValue);
        otherPanel.add(blankText2);
        otherPanel.add(timeText);
        otherPanel.add(timeValue);
    }
    private void setHealthPanel(Document document) {
        powerText=new JLabel("Power Supply");
        powerText.setFont(new Font(SANS_SERIF, Font.BOLD, 32));
        powerText.setHorizontalAlignment(CENTER);
        healthLayout=new GridBagLayout();
        healthLayout.setConstraints(powerText, constraints);
        String powerSupply=((Document)document.get("Health")).getString("Power Supply");
        powerState=!powerSupply.equalsIgnoreCase("false");
        powerBoxOn=new JCheckBox("On", true);
        powerBoxOn.setSelected(powerState);
        powerBoxOn.setSelectedIcon(new ImageIcon(
            "F:/suhas_bits/Stage3/src/SwingUtilities/images/Check_mark.png"));
        powerBoxOn.setIcon(new ImageIcon(
            "F:/suhas_bits/Stage3/src/SwingUtilities/images/Empty_box.png"));
        powerBoxOn.setHorizontalAlignment(CENTER);
        powerBoxOn.setFont(new Font(SANS_SERIF, TRUETYPE_FONT, 32));
        powerBoxOn.addActionListener((ActionEvent e)->{
            powerBoxOn.setSelected(powerState);
        });
        constraints.gridwidth=1;
        healthLayout.setConstraints(powerBoxOn, constraints);
        powerBoxOff=new JCheckBox("Off");
        powerBoxOff.setSelected(!powerState);
        powerBoxOff.setSelectedIcon(new ImageIcon(
            "F:/suhas_bits/Stage3/src/SwingUtilities/images/Check_mark.png"));
        powerBoxOff.setIcon(new ImageIcon(
            "F:/suhas_bits/Stage3/src/SwingUtilities/images/Empty_box.png"));
        powerBoxOff.setHorizontalAlignment(CENTER);
        powerBoxOff.setFont(new Font(SANS_SERIF, TRUETYPE_FONT, 32));
        powerBoxOff.addActionListener((ActionEvent e)->{
            powerBoxOff.setSelected(!powerState);
        });
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        healthLayout.setConstraints(powerBoxOff, constraints);
        
        blankText3=new JLabel("    ");
        blankText3.setFont(new Font(SANS_SERIF, TRUETYPE_FONT, 32));
        blankText3.setHorizontalAlignment(CENTER);
        healthLayout.setConstraints(blankText3, constraints);
        
        syncText=new JLabel("Sync");
        syncText.setFont(new Font(SANS_SERIF, Font.BOLD, 32));
        syncText.setHorizontalAlignment(CENTER);
        healthLayout.setConstraints(syncText, constraints);
        String syncBit=((Document)document.get("Health")).getString("Sync Bit");
        syncState=!syncBit.equalsIgnoreCase("false");
        syncBoxOn=new JCheckBox("On");
        syncBoxOn.setSelected(syncState);
        syncBoxOn.setSelectedIcon(new ImageIcon(
            "F:/suhas_bits/Stage3/src/SwingUtilities/images/Check_mark.png"));
        syncBoxOn.setIcon(new ImageIcon(
            "F:/suhas_bits/Stage3/src/SwingUtilities/images/Empty_box.png"));
        syncBoxOn.setHorizontalAlignment(CENTER);
        syncBoxOn.setFont(new Font(SANS_SERIF, TRUETYPE_FONT, 32));
        syncBoxOn.addActionListener((ActionEvent e)->{
            syncBoxOn.setSelected(syncState);
        });
        constraints.gridwidth=1;
        healthLayout.setConstraints(syncBoxOn, constraints);
        syncBoxOff=new JCheckBox("Off");
        syncBoxOff.setSelected(!syncState);
        syncBoxOff.setSelectedIcon(new ImageIcon(
            "F:/suhas_bits/Stage3/src/SwingUtilities/images/Check_mark.png"));
        syncBoxOff.setIcon(new ImageIcon(
            "F:/suhas_bits/Stage3/src/SwingUtilities/images/Empty_box.png"));
        syncBoxOff.setHorizontalAlignment(CENTER);
        syncBoxOff.setFont(new Font(SANS_SERIF, TRUETYPE_FONT, 32));
        syncBoxOff.addActionListener((ActionEvent e)->{
            syncBoxOff.setSelected(!syncState);
        });
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        healthLayout.setConstraints(syncBoxOff, constraints);
        
        blankText4=new JLabel("    ");
        blankText4.setFont(new Font(SANS_SERIF, TRUETYPE_FONT, 32));
        blankText4.setHorizontalAlignment(CENTER);
        healthLayout.setConstraints(blankText4, constraints);
        
        sensorText=new JLabel("Sensor Connected");
        sensorText.setFont(new Font(SANS_SERIF, Font.BOLD, 32));
        sensorText.setHorizontalAlignment(CENTER);
        healthLayout.setConstraints(sensorText, constraints);
        String sensorConnected=((Document)document.get("Health")).getString("Sensor Connected");
        sensorState=!sensorConnected.equalsIgnoreCase("false");
        sensorBoxOn=new JCheckBox("On");
        sensorBoxOn.setSelected(sensorState);
        sensorBoxOn.setSelectedIcon(new ImageIcon(
            "F:/suhas_bits/Stage3/src/SwingUtilities/images/Check_mark.png"));
        sensorBoxOn.setIcon(new ImageIcon(
            "F:/suhas_bits/Stage3/src/SwingUtilities/images/Empty_box.png"));
        sensorBoxOn.setHorizontalAlignment(CENTER);
        sensorBoxOn.setFont(new Font(SANS_SERIF, TRUETYPE_FONT, 32));
        sensorBoxOn.addActionListener((ActionEvent e)->{
            sensorBoxOn.setSelected(sensorState);
        });
        constraints.gridwidth=1;
        healthLayout.setConstraints(sensorBoxOn, constraints);
        sensorBoxOff=new JCheckBox("Off");
        sensorBoxOff.setSelected(!sensorState);
        sensorBoxOff.setSelectedIcon(new ImageIcon(
            "F:/suhas_bits/Stage3/src/SwingUtilities/images/Check_mark.png"));
        sensorBoxOff.setIcon(new ImageIcon(
            "F:/suhas_bits/Stage3/src/SwingUtilities/images/Empty_box.png"));
        sensorBoxOff.setHorizontalAlignment(CENTER);
        sensorBoxOff.setFont(new Font(SANS_SERIF, TRUETYPE_FONT, 32));
        sensorBoxOff.addActionListener((ActionEvent e)->{
            sensorBoxOff.setSelected(!sensorState);
        });
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        healthLayout.setConstraints(sensorBoxOff, constraints);
        
        healthPanel=new JPanel(healthLayout);
        healthPanel.add(powerText);
        healthPanel.add(powerBoxOn);
        healthPanel.add(powerBoxOff);
        healthPanel.add(blankText3);
        healthPanel.add(syncText);
        healthPanel.add(syncBoxOn);
        healthPanel.add(syncBoxOff);
        healthPanel.add(blankText4);
        healthPanel.add(sensorText);
        healthPanel.add(sensorBoxOn);
        healthPanel.add(sensorBoxOff);
    }
}