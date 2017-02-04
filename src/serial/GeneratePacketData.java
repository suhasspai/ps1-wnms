package serial;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import database.DatabaseHandler;
import java.util.ArrayList;
import resources.DataBuffers;

/**
 *
 * @author WSN
 */

public class GeneratePacketData implements Runnable {
    public void terminate() {
        running=false;
    }
    public void setRunning(boolean running) {
        this.running=running;
    }
    @Override
    public void run() {
        while (running) {
            if (DataBuffers.isValidFrame()) {
                DataBuffers.setValidFrame(false);
                if (DataBuffers.getFrameBuffer().get(0)==(byte)0x91) {
                    if (DataBuffers.getFrameBuffer().get(19)==(byte)0xd1 ||
                        DataBuffers.getFrameBuffer().get(19)==(byte)0xd2) {
                        DatabaseHandler.setup(DataBuffers.getFrameBuffer().subList(18, DataBuffers.getFrameBuffer().size()));
                    }
                    else
                        DatabaseHandler.setupBad(DataBuffers.getFrameBuffer());
                }
                else
                    DatabaseHandler.setupBad(DataBuffers.getFrameBuffer());
            } else
                DatabaseHandler.setupBroken(DataBuffers.getFrameBuffer());
            DataBuffers.getFrameBuffer().clear();
            terminate();
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Arrays.toString(e.getStackTrace());
            }
        }
    }
    
    private boolean running=false;
}
