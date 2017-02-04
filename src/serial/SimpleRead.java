package serial;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.TooManyListenersException;
import java.util.concurrent.TimeUnit;
import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;
import resources.DataBuffers;
import serial.FilterSerialData;

/**
 *
 * @author Suhas S Pai
 */

public class SimpleRead implements Runnable, SerialPortEventListener {
    public SimpleRead(CommPortIdentifier portId) {
        try {
            serialPort=(SerialPort)portId.open("SimpleRead", 200);
        } catch (PortInUseException e) {
            System.out.println(e);
        }
        try {
            inputStream=serialPort.getInputStream();
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e) {
            System.out.println(e);
        }
        serialPort.notifyOnDataAvailable(true);
        try {
            serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
            serialPort.setDTR(false);
            serialPort.setRTS(false);
        } catch (UnsupportedCommOperationException e) {
            System.out.println(e);
        }
        System.out.println("Ready to read from serial port.");
        FSD=new FilterSerialData();
    }
    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    @Override
    public void serialEvent(SerialPortEvent event) {
        switch(event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                try {
                    if (inputStream.available() > 0) {
                        Integer size=inputStream.read(DataBuffers.getInputBuffer());
                        DataBuffers.setInputBufferSize(size);
                        FSD.send();
                        //System.out.println("Meh");
                        FSD.obtainFrame();
                        //MongoDBJDBC.main(arguments);
                    }
                } catch (IOException e) {
                    System.out.println(Arrays.toString(e.getStackTrace()));
                }
                break;
        }
    }
    
    //private static Enumeration portList; // list of available ports
    private InputStream inputStream;
    private SerialPort serialPort;
    private FilterSerialData FSD;
    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();
}