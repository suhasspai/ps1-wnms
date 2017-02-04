package packets.datablocks;

import static java.util.Arrays.copyOfRange;

/**
 *
 * @author Suhas S Pai
 */

public class SensorDataBlock {
    public SensorDataBlock(byte[] sensorTypeInfo) {
        setSensorInfo(new Byte(sensorTypeInfo[0]).intValue());
        setValue(copyOfRange(sensorTypeInfo, 1, 1+getDataType()));
    }
    public String getSensorType() {
        return sensorType;
    }
    public Integer getValue() {
        return value;
    }
    public final Integer getDataType() {
        return dataType;
    }
    
    private String sensorType;
    private Integer dataType, value;
    private void setValue(byte[] value) {
        this.value=0;
        for (int i=0; i<value.length; i++) {
            this.value<<=8;
            this.value+=Byte.toUnsignedInt(value[i]); // decode this into a value
        }
    }
    private void setSensorInfo(int sensorTypeInfo) {
        dataType=sensorTypeInfo%4;
        if (dataType<3)
            dataType=new Double(Math.pow(2, dataType)).intValue();
        int type=sensorTypeInfo/8;
        switch (type) {
            case 0:
                sensorType="Temperature (C)";
                break;
            case 1:
                sensorType="Humidity (%)";
                break;
            case 2:
                sensorType="Vibration";
                break;
            case 3:
                sensorType="Leak";
                break;
            case 4:
                sensorType="Radiation (mRad/hr)";
                break;
            case 5:
                sensorType="Radiation (uRad/hr)";
                break;
            case 6:
                sensorType="Radiation (mRad/hr) 4-digit display";
                break;
            default:
                sensorType="Error";
        }
    }
}
