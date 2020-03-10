package frc.robot.vision;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
//import com.fazecast.jSerialComm.SerialPort;

import frc.robot.utils.JStruct;

public class OpenMV {
    private static final int MAX_READ_BUFF_BYTES = (int) (115200 * 0.02 / 8 * 10);
    private JStruct struct = new JStruct();
    private SerialPort connection;

    private static int FB_HDR_SIZE = 12;

    // USB Debug commands
    private static byte USBDBG_CMD = (byte) 48;
    private static byte USBDBG_FW_VERSION = (byte) 0x80;
    private static byte USBDBG_FRAME_SIZE = (byte) 0x81;
    private static byte USBDBG_FRAME_DUMP = (byte) 0x82;
    private static byte USBDBG_ARCH_STR = (byte) 0x83;
    private static byte USBDBG_SCRIPT_EXEC = (byte) 0x05;
    private static byte USBDBG_SCRIPT_STOP = (byte) 0x06;
    private static byte USBDBG_SCRIPT_SAVE = (byte) 0x07;
    private static byte USBDBG_SCRIPT_RUNNING = (byte) 0x87;
    private static byte USBDBG_TEMPLATE_SAVE = (byte) 0x08;
    private static byte USBDBG_DESCRIPTOR_SAVE = (byte) 0x09;
    private static byte USBDBG_ATTR_READ = (byte) 0x8A;
    private static byte USBDBG_ATTR_WRITE = (byte) 0x0B;
    private static byte USBDBG_SYS_RESET = (byte) 0x0C;
    private static byte USBDBG_FB_ENABLE = (byte) 0x0D;
    private static byte USBDBG_TX_BUF_LEN = (byte) 0x8E;
    private static byte USBDBG_TX_BUF = (byte) 0x8F;

    private static int ATTR_CONTRAST = (byte) 0;
    private static int ATTR_BRIGHTNESS = (byte) 1;
    private static int ATTR_SATURATION = (byte) 2;
    private static int ATTR_GAINCEILING = (byte) 3;

    private static long BOOTLDR_START = 0xABCD0001;
    private static long BOOTLDR_RESET = 0xABCD0002;
    private static long BOOTLDR_ERASE = 0xABCD0004;
    private static long BOOTLDR_WRITE = 0xABCD0008;

    public OpenMV(int baudRate,Port p, int timeout) {
        /*for(SerialPort port: SerialPort.getCommPorts()){
            if(port.getPortDescription().contains("OpenMV")){
                System.out.println(port.getPortDescription());
                connection = port;
                break;
            }
        }*/
        connection = new SerialPort(baudRate, p);
        //connection.setBaudRate(baudRate);

        //connection.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, timeout, timeout);
        connection.setTimeout(timeout);
        //connection.openPort();
        //connection.readBytes(new byte[connection.bytesAvailable()], connection.bytesAvailable());
        cleanSerialInput();
        //System.out.println("Connected: "+connection.isOpen());
    }

    private void write(byte[] bytes) {
        connection.write(bytes, bytes.length);
    }

    public void cleanSerialInput(){
        connection.read(connection.getBytesReceived());
    }

    private byte[] read(int numBytes) throws Exception {
        if (numBytes < MAX_READ_BUFF_BYTES){
            var buff = new byte[numBytes];

            buff = connection.read(numBytes);

            //System.out.println(new String(buff));
            if(buff.length != numBytes){
                throw new Exception(String.format("Requested %x bytes but got %x", numBytes, buff.length));
            }
            return buff;
        } else throw new Exception(String.format( "Requested read size too big! Requested %x but max size %x", numBytes, MAX_READ_BUFF_BYTES));
    }

    public long[] fbSize() throws Exception {
        write(struct.pack("<BBI", USBDBG_CMD, USBDBG_FRAME_SIZE, FB_HDR_SIZE));
        var buffer = read(12);
        return struct.unpack("III", buffer);
    }

    public void execScript(byte[] buff) throws Exception {
        write(struct.pack("<BBI", USBDBG_CMD,USBDBG_SCRIPT_EXEC, buff.length));
        write(buff);
    }

    public void stopScript() throws Exception {
        write(struct.pack("<BBI", USBDBG_CMD,USBDBG_SCRIPT_STOP,0));
    }

    public boolean scriptRunning() throws Exception {
        write(struct.pack("<BBI", USBDBG_CMD, USBDBG_SCRIPT_RUNNING, 4));
        return struct.unpack("I", read(4))[0] != 0;
    }

    public void reset() throws Exception {
        write(struct.pack("<BBI", USBDBG_CMD, USBDBG_SYS_RESET, 0));
    }

    public boolean bootloaderStart() throws Exception {
        write(struct.pack("<I", BOOTLDR_START));
        return struct.unpack("I", read(4))[0] == BOOTLDR_START;
    }

    public void bootloaderReset() throws Exception {
        write(struct.pack("<I", BOOTLDR_RESET));
    }

    public void flashErase(byte sector) throws Exception {
        write(struct.pack("<II", BOOTLDR_ERASE, sector));
    }

    public int txBufLen() throws Exception {
        write(struct.pack("<BBI", USBDBG_CMD, USBDBG_TX_BUF_LEN, 4));
        return (int)struct.unpack("I", read(4))[0];
    }

    public byte[] txBuf(int i) throws Exception {
        write(struct.pack("<BBI", USBDBG_CMD, USBDBG_TX_BUF, i));
        return read(i);
    }

    public long[] fwVersion() throws Exception {
        write(struct.pack("<BBI", USBDBG_CMD, USBDBG_FW_VERSION, 12));
        return struct.unpack("III", read(12));
    }

    public void enableFb(int enable) throws Exception {
        write(struct.pack("<BBI", USBDBG_CMD, USBDBG_FB_ENABLE, 4));
        write(struct.pack("<I", enable));
    }

    public byte[] fbDump() throws Exception {
        long[] size = fbSize();
        if (size[0] == 0){
            return new byte[0];
        }
        System.out.println("Here!");
        long numBytes = 0;
        if (size[2] > 2){ //JPEG
            numBytes = size[2];
        }
        // read fb data
        write(struct.pack("<BBI", (long)USBDBG_CMD, (long)USBDBG_FRAME_DUMP, numBytes));
        return read((int)numBytes);
    }

	public byte[] getSerialOutput() throws Exception {
		return txBuf(txBufLen());
    }
    
    public void close(){
        connection.close();
    }
}