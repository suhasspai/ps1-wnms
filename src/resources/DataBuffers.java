package resources;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Suhas S Pai
 */

public class DataBuffers {
    public static void setInputBufferSize(Integer size) {
        DataBuffers.inputBufferSize=size;
    }
    public static Integer getInputBufferSize() {
        return inputBufferSize;
    }
    public static void setFrameBuffer(Integer size) {
        frameBuffer=new ArrayList<>(size);
    }
    public static byte[] getInputBuffer() {
        return DataBuffers.inputBuffer;
    }
    public static ArrayList<Byte> getFrameBuffer() {
        return DataBuffers.frameBuffer;
    }
    public static boolean isValidFrame() {
        return validFrame;
    }
    public static void setValidFrame(boolean validFrame) {
        DataBuffers.validFrame=validFrame;
    }
    
    private static Integer inputBufferSize=0;
    private static byte[] inputBuffer=new byte[500]; // data read from port is stored here
    private static boolean validFrame=false;
    private static ArrayList<Byte> frameBuffer;
}
