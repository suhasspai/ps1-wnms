package serial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import resources.DataBuffers;

/**
 *
 * @author Suhas S Pai
 */

public class FilterSerialData {
    public FilterSerialData() {
        data=new ArrayList<>();
        this.size=0;
        index=0;
        status=0;
    }
    public static String bytesToHex(Byte[] bytes) {
        char[] hexChars=new char[bytes.length*3];
        for (int j=0; j<bytes.length; j++) {
            int v=bytes[j]&0xFF;
            hexChars[j*3]=hexArray[v>>>4];
            hexChars[j*3+1]=hexArray[v&0x0F];
            hexChars[j*3+2]=' ';
        }
        return new String(hexChars);
    }
    public void send() {
        for (int i=0; i<DataBuffers.getInputBufferSize(); i++)
            data.add(DataBuffers.getInputBuffer()[i]);
        index=0;
        //System.out.println(data);
        //System.out.println(bytesToHex(Arrays.copyOf(DataBuffers.getInputBuffer(), DataBuffers.getInputBufferSize())));
        //data.clear();
    }
    public void obtainFrame() {        
        start:
        switch(status) {
            case 0:
                index=detect7E();
                if (index<0) {
                    data.clear();;
                    return;
                }
                status++;
                index++;
                if (index>=data.size())
                    break;
                //System.out.println("0");
            case 1:
                setSizeMSB();
                status++;
                index++;
                if (index>=data.size())
                    break;
                //System.out.println("1");
            case 2:
                setSizeLSB();
                dataLeft=size;
                status++;
                index++;
                if (index>=data.size())
                    break;
                //System.out.println("2");
            case 3:
                setFrame(size);
                int len=Math.min(dataLeft, data.size()-index);
                //DataBuffers.lock();
                if (!Objects.equals(DataBuffers.getFrameBuffer().size(), size)) {
                    DataBuffers.getFrameBuffer().addAll(data.subList(index, index+len));
                    dataLeft-=len;
                    index+=len;
                }
                if (dataLeft==0) {
                    size=0;
                    status++;
                }
                if (index>=data.size())
                    break;
                //System.out.println("3");
            case 4:
                Byte[] a={};
                System.out.println(bytesToHex(DataBuffers.getFrameBuffer().toArray(a)));
                byte b=checkSum();
                DataBuffers.setValidFrame(Objects.equals(data.get(index), b));
                GeneratePacketData GPD=new GeneratePacketData();
                handleThread(GPD);
                //System.out.println(b);
                //DataBuffers.unlock();
                status=0;
                this.size=0;
                index++;
                if (index<data.size())
                    break start;
        }
        data.clear();
    }

    private ArrayList<Byte> data;
    private int index;
    private Integer size, status, dataLeft;
    private Thread thread;
    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private void setFrame(Integer size) {
        DataBuffers.setFrameBuffer(size);
    }
    private Integer detect7E() {
        return data.indexOf((byte)0x7e);
    }
    private void setSizeMSB() {
        size=Byte.toUnsignedInt(data.get(index));
        size<<=8;
    }
    private void setSizeLSB() {
        size+=Byte.toUnsignedInt(data.get(index));
    }
    private byte checkSum() {
        byte sum=0;
        for (int i=0; i<DataBuffers.getFrameBuffer().size(); i++) {
            sum=(byte)((sum+DataBuffers.getFrameBuffer().get(i))&0xFF);
        }
        sum=(byte)((0xFF-sum)&0xFF);
        return sum;
    }
    private void handleThread(GeneratePacketData GPD) {
        thread=new Thread(GPD);
        GPD.setRunning(true);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(FilterSerialData.class.getName()).log(Level.SEVERE, null, ex);
        }
        GPD.terminate();
    }
}
