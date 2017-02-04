package packets;

import java.util.ArrayList;

import packets.datablocks.NodeDataBlock;

import org.bson.Document;
import packets.datablocks.Health;
import packets.datablocks.TimeOfDay;

/**
 *
 * @author Suhas S Pai
 */

public class AggregatedPacket {
    public AggregatedPacket(Integer nodeID, Integer groupID, Integer securityID,
            Integer packetID, Integer no_of_nodes, Byte health, Integer hours,
            Integer minutes, Integer seconds, Integer length) {
        this.nodeID=nodeID;
        this.groupID=groupID;
        this.securityID=securityID;
        this.packetID=packetID;
        this.length=length;
        setHealth(health);
        setTime(hours, minutes, seconds);
        setNo_of_nodes(no_of_nodes);
        setLength(length);
        this.nodeBlocks=new ArrayList<>(no_of_nodes);
    }
    public Integer getNodeID() {
        return nodeID;
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
    public Integer getLength() {
        return length;
    }
    public Integer getNo_of_nodes() {
        return no_of_nodes;
    }
    public Document getHealth() {
        return health.getAsDocument();
    }
    public String getTime() {
        return time.toString();
    }
    public void addNodeDataBlock(NodeDataBlock nodeDataBlock) {
        nodeBlocks.add(nodeDataBlock);
    }
    public NodeDataBlock getNodeDataBlock(Integer index) {
        return nodeBlocks.get(index);
    }
    public ArrayList<Document> getNodesAsDocument() {
        ArrayList<Document> listOfNodes=new ArrayList<>(no_of_nodes);
        for (int i=0; i<getNo_of_nodes(); i++) {
            listOfNodes.add(nodeBlocks.get(i).getAsDocument());
        }
        return listOfNodes;
    }

    private final Integer nodeID, groupID, securityID, packetID;
    private Integer length, no_of_nodes;
    private Health health;
    private TimeOfDay time;
    private final ArrayList<NodeDataBlock> nodeBlocks;
    private void setHealth(Byte health) {
        this.health=new Health(health);
    }
    private void setTime(Integer hours, Integer minutes, Integer seconds) {
        time=new TimeOfDay(hours, minutes, seconds);
    }
    private void setNo_of_nodes(Integer no_of_nodes) {
        this.no_of_nodes = no_of_nodes;
    }
    private void setLength(Integer length) {
        this.length = length;
    }
}
