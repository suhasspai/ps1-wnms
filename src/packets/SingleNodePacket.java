package packets;

import org.bson.Document;
import packets.datablocks.MeasurementBlock;
import packets.datablocks.NodeDataBlock;
import packets.datablocks.TimeOfDay;

/**
 *
 * @author Suhas S Pai
 */

public class SingleNodePacket {
    public SingleNodePacket(Integer nodeID, Integer groupID, Integer securityID, Integer packetID,
            Integer no_of_sensor_values, Byte health, Integer hours, Integer minutes,
            Integer seconds, Integer length) {
        this.nodeBlock=new NodeDataBlock(nodeID, no_of_sensor_values, health);
        this.groupID=groupID;
        this.securityID=securityID;
        this.packetID=packetID;
        this.length=length;
        setTime(hours, minutes, seconds);
        setLength(length);
        this.measurementBlock=new MeasurementBlock(no_of_sensor_values);
    }
    public MeasurementBlock getMeasurementBlock() {
        return this.measurementBlock;
    }
    public Integer getNodeID() {
        return nodeBlock.getNodeID();
    }
    public Integer getGroupID() {
        return groupID;
    }
    public Integer getSecurityID() {
        return securityID;
    }
    public Integer getPacketID() {
        return packetID;
    }
    public Integer getNo_of_sensor_values() {
        return nodeBlock.getNo_of_sensor_values();
    }
    public Document getHealth() {
        return nodeBlock.getHealth();
    }
    public String getTime() {
        return time.toString();
    }
    public Integer getValue() {
        for (int i=0; i<measurementBlock.size(); i++) {
            if (measurementBlock.getSensorDataBlock(i).getSensorType().equals("Radiation (mRad/hr)")) {
                return measurementBlock.getSensorDataBlock(i).getValue();
            }
        }
        return -1;
    }
    
    private final MeasurementBlock measurementBlock;
    private final NodeDataBlock nodeBlock;
    private final Integer groupID, securityID, packetID;
    private Integer length;
    private TimeOfDay time;
    private void setTime(Integer hours, Integer minutes, Integer seconds) {
        time=new TimeOfDay(hours, minutes, seconds);
    }
    private void setLength(Integer length) {
        this.length = length;
    }
}
