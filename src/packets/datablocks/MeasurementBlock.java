package packets.datablocks;

import java.util.ArrayList;

/**
 *
 * @author Suhas S Pai
 */

public class MeasurementBlock {
    public MeasurementBlock(Integer no_of_sensor_values) {
        block=new ArrayList<>(no_of_sensor_values);
    }
    public void addSensorDataBlock(SensorDataBlock sensorblock) {
        block.add(sensorblock);
    }
    public ArrayList<SensorDataBlock> getBlock() {
        return this.block;
    }
    public SensorDataBlock getSensorDataBlock(Integer sensorNo) {
        return block.get(sensorNo);
    }
    public Integer size() {
        return block.size();
    }
    
    private final ArrayList<SensorDataBlock> block;
}
