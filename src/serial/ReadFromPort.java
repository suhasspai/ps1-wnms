package serial;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;

/**
 *
 * @author Suhas S Pai
 */

public class ReadFromPort {
    public static void read() {
        try {
        //portList = CommPortIdentifier.getPortIdentifiers();
        //try {
            //while (portList.hasMoreElements()) {
            portId = CommPortIdentifier.getPortIdentifier("COM34"); 
                /*portId=(CommPortIdentifier)portList.nextElement();
                System.out.println(portId.getName());
                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    if (portId.getName().equals("COM34")) {
                    //if (portId.getName().equals("/dev/ttyS0")) {
                        SimpleRead reader = new SimpleRead();
                    }
                }
            }*/
        } catch (NoSuchPortException e) {
            System.out.println("Could not find COM port.");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        SimpleRead reader = new SimpleRead(portId);
        ExecutorService executorService=Executors.newCachedThreadPool();
        executorService.execute(reader);
        executorService.execute(new GeneratePacketData());
        executorService.shutdown();
        //readThread = new Thread(reader);
        //readThread.start();

    }
    
    private static CommPortIdentifier portId;
}