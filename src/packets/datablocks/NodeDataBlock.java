package packets.datablocks;

import org.bson.Document;

/**
 *
 * @author Suhas S Pai
 */

public class NodeDataBlock {
    public NodeDataBlock(Integer nodeID, Integer no_of_sensor_values, Byte health) {
        this.nodeID=nodeID;
        this.no_of_sensor_values=no_of_sensor_values;
        this.health=new Health(health);
        this.measurementBlock=new MeasurementBlock(no_of_sensor_values);
    }
    public MeasurementBlock getMeasurementBlock() {
        return this.measurementBlock;
    }
    public Integer getNodeID() {
        return this.nodeID;
    }
    public Integer getNo_of_sensor_values() {
        return this.no_of_sensor_values;
    }
    public Document getHealth() {
        return this.health.getAsDocument();
    }
    public Document getAsDocument() {
        Document nodeDataBlock=new Document("NodeID", getNodeID()), valueBlock=new Document();
        for (int i=0; i<this.getMeasurementBlock().size(); i++) {
            valueBlock.append(this.getMeasurementBlock().getSensorDataBlock(i).getSensorType(),
                    this.measurementBlock.getSensorDataBlock(i).getValue());
        }
        nodeDataBlock.append("Data", valueBlock)
        .append("Health", this.getHealth());
        return nodeDataBlock;
    }
    public void setMeasurementBlock(MeasurementBlock measurementBlock) {
        this.measurementBlock.getBlock().addAll(0, measurementBlock.getBlock());
    }
    
    private final Integer nodeID, no_of_sensor_values;
    private final Health health;
    private final MeasurementBlock measurementBlock;
}