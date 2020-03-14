
package frc.robot.vision;

/**
 *  Original source by Peter Labadorf
 */
import java.io.File;
import java.io.FileOutputStream;

import java.util.logging.Logger;


public class JOpenMV {

    public JOpenMV(SerialDataSource serialDataSource, String scriptToRun) {
        this.serialDataSource = serialDataSource;
        this.scriptToRun = scriptToRun;
    }    
    
    private JStruct struct = new JStruct();

    private static int FB_HDR_SIZE   = 12;

    //USB Debug commands
    private static byte USBDBG_CMD            = (byte) 48  ;
    private static byte USBDBG_FW_VERSION     = (byte) 0x80;
    private static byte USBDBG_FRAME_SIZE     = (byte) 0x81;
    private static byte USBDBG_FRAME_DUMP     = (byte) 0x82;
    private static byte USBDBG_ARCH_STR       = (byte) 0x83;
    private static byte USBDBG_SCRIPT_EXEC    = (byte) 0x05;
    private static byte USBDBG_SCRIPT_STOP    = (byte) 0x06;
    private static byte USBDBG_SCRIPT_SAVE    = (byte) 0x07;
    private static byte USBDBG_SCRIPT_RUNNING = (byte) 0x87;
    private static byte USBDBG_TEMPLATE_SAVE  = (byte) 0x08;
    private static byte USBDBG_DESCRIPTOR_SAVE= (byte) 0x09;
    private static byte USBDBG_ATTR_READ      = (byte) 0x8A;
    private static byte USBDBG_ATTR_WRITE     = (byte) 0x0B;
    private static byte USBDBG_SYS_RESET      = (byte) 0x0C;
    private static byte USBDBG_FB_ENABLE      = (byte) 0x0D;
    private static byte USBDBG_TX_BUF_LEN     = (byte) 0x8E;
    private static byte USBDBG_TX_BUF         = (byte) 0x8F;

    private static int ATTR_CONTRAST         = (byte) 0;
    private static int ATTR_BRIGHTNESS       = (byte) 1;
    private static int ATTR_SATURATION       = (byte) 2;
    private static int ATTR_GAINCEILING      = (byte) 3;

    private static long BOOTLDR_START         = 0xABCD0001;
    private static long BOOTLDR_RESET         = 0xABCD0002;
    private static long BOOTLDR_ERASE         = 0xABCD0004;
    private static long BOOTLDR_WRITE         = 0xABCD0008;

    private final static Logger log = Logger.getLogger(JOpenMV.class.getName());
    private String scriptToRun = "";
    private SerialDataSource serialDataSource;
    
    static {
        System.setProperty("java.util.logging.config.file","logging.properties");
    }  

    
    public static void main(String[] args) throws Exception {
        log.info("Starting...");
        SerialDataSource sd = JSerialCommSerialDataSource.openMV(921000, 3);
        String helloWorld = new ClassLoaderScriptProvider().loadScript("vision.py");
        JOpenMV openMV = new JOpenMV( sd,helloWorld);
        openMV.enableFb(5);
        
        log.warning("Script Running=" + openMV.scriptRunning());
        openMV.stopScript();
        
        log.warning("Running Script hello.py");
        openMV.execScript(helloWorld.getBytes());
        log.warning("Script Running=" + openMV.scriptRunning());
        while(true){ 
            //openMV.dumpFrameToFile("test.jpg");
            String s = new String(openMV.readTxBuf());
            log.warning(String.format("Read %d Bytes",s.length() ));
            System.out.println(s);
            Thread.sleep(200);
            //System.out.println(new String(openMV.txBuf(openMV.txBufLen())));
        }
        
    }

    private void write(byte[] bytes) {
        serialDataSource.writeBytes(bytes);
    }

    private byte[] read(int numBytes) {
        return serialDataSource.readBytes(numBytes);
 
    }

    public long[] fbSize()  throws Exception{
        write(struct.pack("<BBI", USBDBG_CMD, USBDBG_FRAME_SIZE, FB_HDR_SIZE));
        var buffer = read(12);
        return struct.unpack("III", buffer);
    }

    public void execScript(byte[] buff)  throws Exception{
        write(struct.pack("<BBI", USBDBG_CMD,USBDBG_SCRIPT_EXEC, buff.length));
        write(buff);
    }

    public void stopScript()  throws Exception{
        write(struct.pack("<BBI", USBDBG_CMD,USBDBG_SCRIPT_STOP,0));
    }

    public boolean scriptRunning()  throws Exception{
        write(struct.pack("<BBI", USBDBG_CMD, USBDBG_SCRIPT_RUNNING, 4));
        return struct.unpack("I", read(4))[0] != 0;
    }

    public void reset()  throws Exception{
        write(struct.pack("<BBI", USBDBG_CMD, USBDBG_SYS_RESET, 0));
    }

    public boolean bootloaderStart()  throws Exception{
        write(struct.pack("<I", BOOTLDR_START));
        return struct.unpack("I", read(4))[0] == BOOTLDR_START;
    }

    public void bootloaderReset()  throws Exception{
        write(struct.pack("<I", BOOTLDR_RESET));
    }

    public void flashErase(byte sector) throws Exception {
        write(struct.pack("<II", BOOTLDR_ERASE, sector));
    }

    public int txBufLen()  throws Exception{
        write(struct.pack("<BBI", USBDBG_CMD, USBDBG_TX_BUF_LEN, 4));
        return (int)struct.unpack("I", read(4))[0];
    }

    public byte[] txBuf(int i) throws Exception{
        write(struct.pack("<BBI", USBDBG_CMD, USBDBG_TX_BUF, i));
        return read(i);
    }

    public byte[] readTxBuf() throws Exception{
        int LIMIT=1000000;
        if ( txBufLen() > LIMIT){
            System.out.println("WARNING! Too MUCH DATA TO READ: " + txBufLen());
            return txBuf ( LIMIT);
        }
        else{
            return txBuf(txBufLen());
        }
        
    }
    
    public long[] fwVersion() throws Exception{
        write(struct.pack("<BBI", USBDBG_CMD, USBDBG_FW_VERSION, 12));
        return struct.unpack("III", read(12));
    }

    public void enableFb(int enable)  throws Exception{
        write(struct.pack("<BBI", USBDBG_CMD, USBDBG_FB_ENABLE, 4));
        write(struct.pack("<I", enable));
    }

    public byte[] getFrame() throws Exception{

        long[] size = fbSize();
        if (size[0] == 0){
            return null;
        }
        long numBytes = 0;
        if (size[2] > 2){ //JPEG
            numBytes = size[2];
        }
        // read fb data
        write(struct.pack("<BBI", (long)USBDBG_CMD, (long)USBDBG_FRAME_DUMP, numBytes));
        byte[] buff = read((int)numBytes);
        return buff;

    }
    
    public void dumpFrameToFile(String s)  throws Exception{
        byte[] bytes = getFrame();
        FileOutputStream output = new FileOutputStream(new File(s));
        output.write(bytes);
        output.close();
    }

}