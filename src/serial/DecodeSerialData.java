package serial;

import java.util.Arrays;

import packets.AggregatedPacket;
import packets.SingleNodePacket;
import packets.datablocks.MeasurementBlock;
import packets.datablocks.NodeDataBlock;
import packets.datablocks.SensorDataBlock;

/**
 *
 * @author Suhas S Pai
 */

public class DecodeSerialData {
    public DecodeSerialData(byte[] array) { // constructor
        setSinglePacket((byte)0xd1); // Packet ID is 'D1' for a single packet
        this.serialData=array;
        this.length=(Integer)((Byte.toUnsignedInt(this.serialData[2]))+3); // gives remaining length
                                                                        // of the packet
        setSingle(false);
        setAggregate(false);
        singleOrAgg(); // to check whether packet is from a single node or cluster head
    }
    public boolean isSingle() { // method to check whether the currently parsed packet is single
        return single;
    }
    public boolean isAggregate() { // method to check whether the currently parsed packet is aggregated
        return aggregate;
    }
    public SingleNodePacket decodeSinglePacket() { // method to decode single packet
        Integer NodeID=(Integer)(Byte.toUnsignedInt(serialData[4])),
            GroupID=(Integer)(Byte.toUnsignedInt(serialData[3])),
            SecurityID=(Integer)(Byte.toUnsignedInt(serialData[0])),
            PacketID=(Integer)(Byte.toUnsignedInt(serialData[1])),
            No_of_sensor_values=(Integer)(Byte.toUnsignedInt(serialData[6])),
            Hours=(Integer)(Byte.toUnsignedInt(serialData[length-3])),
            Minutes=(Integer)(Byte.toUnsignedInt(serialData[length-2])),
            Seconds=(Integer)(Byte.toUnsignedInt(serialData[length-1]));
        NodeID<<=8;
        NodeID+=(Byte.toUnsignedInt(serialData[5]));
        Byte Health=serialData[length-4];
        SingleNodePacket SNP=new SingleNodePacket(NodeID, GroupID, SecurityID, PacketID,
            No_of_sensor_values, Health, Hours, Minutes, Seconds, length);
        int index=7;
        for (int i=0; i<SNP.getNo_of_sensor_values(); i++) {
            SensorDataBlock SDB=new SensorDataBlock(Arrays.copyOfRange(serialData, index,
                    length-4));
            SNP.getMeasurementBlock().addSensorDataBlock(SDB);
            index+=(1+SDB.getDataType());
        }
        return SNP;
    }
    public AggregatedPacket decodeAggregatedPacket() { // method to decode aggregated packet
        Integer NodeID=(Integer)(Byte.toUnsignedInt(serialData[4])),
            GroupID=(Integer)(Byte.toUnsignedInt(serialData[3])),
            SecurityID=(Integer)(Byte.toUnsignedInt(serialData[0])),
            PacketID=(Integer)(Byte.toUnsignedInt(serialData[1])),
            No_of_nodes=(Integer)(Byte.toUnsignedInt(serialData[6])),
            Hours=(Integer)(Byte.toUnsignedInt(serialData[length-3])),
            Minutes=(Integer)(Byte.toUnsignedInt(serialData[length-2])),
            Seconds=(Integer)(Byte.toUnsignedInt(serialData[length-1]));
        NodeID<<=8;
        NodeID+=(Byte.toUnsignedInt(serialData[5]));
        Byte Health=serialData[length-4];
        AggregatedPacket AP=new AggregatedPacket(NodeID, GroupID, SecurityID, PacketID,
            No_of_nodes, Health, Hours, Minutes, Seconds, length);
        int index=7;
        for (int i=0; i<AP.getNo_of_nodes(); i++) { // for each NodeDataBlock
            Integer nodeID=(Integer)Byte.toUnsignedInt(serialData[index++]);
            nodeID<<=8;
            nodeID+=Byte.toUnsignedInt(serialData[index++]);
            Integer no_of_sensor_values=(Integer)Byte.toUnsignedInt(serialData[index++]);
            MeasurementBlock measurementBlock=new MeasurementBlock(no_of_sensor_values);
            for (int j=0; j<no_of_sensor_values; j++) { // for each sensor value
                SensorDataBlock SDB=new SensorDataBlock(Arrays.copyOfRange(serialData, index,
                    length-4));
                measurementBlock.addSensorDataBlock(SDB);
                index+=(1+SDB.getDataType());
            }
            Byte health=serialData[index++];
            NodeDataBlock NDB=new NodeDataBlock(nodeID, no_of_sensor_values, health);
            NDB.setMeasurementBlock(measurementBlock);
            AP.addNodeDataBlock(NDB);
        }
        return AP;
    }

    private final byte []serialData; // this holds the packet as a string of bytes
    private static Byte singlePacket;
    private final Integer length;
    private boolean single;
    private boolean aggregate;
    // Setter methods
    private void setAggregate(boolean aggregate) {
        this.aggregate = aggregate;
    }
    private void setSingle(boolean single) {
        this.single = single;
    }
    private static void setSinglePacket(Byte singlePacket) {
        DecodeSerialData.singlePacket = singlePacket;
    }
    private void singleOrAgg() { // method to check for single/aggregated packet
        if (singlePacket==serialData[1])
            setSingle(true);
        else
            setAggregate(true);
    }
}