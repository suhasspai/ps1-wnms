package packets.datablocks;

import application.SwingView;
import org.bson.Document;

/**
 *
 * @author Suhas S Pai
 */

public class Health {
    public Health(Byte health) {
        Integer value=Byte.toUnsignedInt(health);
        if (value%2==1)
            setPowerSupply(true);
        else
            setPowerSupply(false);
        value>>=1;
        if (value%2==1)
            setSyncBit(true);
        else
            setSyncBit(false);
        value>>=1;
        if (value%2==1)
            setSensorConnected(true);
        else
            setSensorConnected(false);
        value>>=5;
        if (value%2==1)
            setAlarm(true);
        else
            setAlarm(false);
    }
    public boolean isSyncBit() {
        return syncBit;
    }
    public boolean isPowerSupply() {
        return powerSupply;
    }
    public boolean isSensorConnected() {
        return sensorConnected;
    }
    public boolean isAlarm() {
        return alarm;
    }
    public Document getAsDocument() {
        return new Document("Alarm", Boolean.toString(isAlarm()))
            .append("Sensor Connected", Boolean.toString(isSensorConnected()))
            .append("Sync Bit", Boolean.toString(isSyncBit()))
            .append("Power Supply", Boolean.toString(isPowerSupply()));
    }
    @Override
    public String toString() {
        return "Alarm: "+isAlarm()+", Sensor Connected: "+isSensorConnected()+", Sync Bit: "+isSyncBit()+", Power Supply: "+isPowerSupply();
    }
    
    private boolean alarm, sensorConnected, syncBit, powerSupply;
    private void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }
    private void setSyncBit(boolean syncBit) {
        this.syncBit = syncBit;
    }
    private void setSensorConnected(boolean sensorConnected) {
        this.sensorConnected = sensorConnected;
    }
    private void setPowerSupply(boolean powerSupply) {
        this.powerSupply = powerSupply;
    }
}
