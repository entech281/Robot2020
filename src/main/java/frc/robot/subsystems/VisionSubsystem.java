/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotConstants;
import frc.robot.pose.*;
import frc.robot.utils.JStruct;
import frc.robot.utils.VisionDataProcessor;

import java.io.FileOutputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 *
 * @author aryan and plaba
 */
public class VisionSubsystem extends BaseSubsystem {

    private static final int BAUD_RATE = 115200;
    private static final int TIMEOUT = 2;
    private static final String VISION_SCRIPT_LOCATION = Filesystem.getDeployDirectory() + "python/vision.py";
    private static final int FRAME_RATE = 20;
    
    private OpenMV openMV;

    private VisionData visionData = VisionData.DEFAULT_VISION_DATA;
    private VisionDataProcessor processor;

    private State state = State.OPENMV_NOT_CONNECTED;

    @Override
    public void initialize() {
        logger.log("initialized", true);
        ensureConnected();
        processor = new VisionDataProcessor();
    }

    @Override
    public void customPeriodic(RobotPose rPose, FieldPose fPose) {
        logger.driverinfo("Vision Status", state);

        switch(state){
            case OPENMV_NOT_CONNECTED:
                break; // We could try reconnecting just in case we get vision halfway through the match
            case OPENMV_CONNECTED:


        }

        /*if(isConnected){
            try{
                String reading = openMV.getSerialOutput().toString();
                logger.log("Input", reading);
                logger.log("Script is running", openMV.scriptRunning());
                processor.addInput(reading);
                visionData = processor.getCurrentVisionData();
                logger.log("Vertical offset", visionData.getVerticalOffset());
                logger.driverinfo("Horizontal Offset", visionData.getLateralOffset());
            } catch (Exception e){
                isConnected = false;
            }
        }*/
    }

    public VisionData getVisionData() {
        return visionData;
    }

    public void ensureConnected(){
        if(state == State.OPENMV_NOT_CONNECTED){
            try{
                openMV = new OpenMV(BAUD_RATE, TIMEOUT);
                logger.driverinfo("Vision connection initialization", "SUCCESS");
                state = State.OPENMV_CONNECTED;
            } catch(Exception e){
                logger.driverinfo("Vision connection initialization", "FAILED");
            }
        }
    }

    public boolean isConnected(){
        return state != State.OPENMV_NOT_CONNECTED;
    }

    private enum State{
        OPENMV_NOT_CONNECTED,
        OPENMV_CONNECTED,
        STOPPED_SCRIPT,
        STARTED_SCRIPT,
        SCRIPT_RUNNING
    }

    private class OpenMV{
        private JStruct struct = new JStruct();
        private SerialPort connection;

        private static final int FB_HDR_SIZE   = 12;

        //USB Debug commands
        private static final byte USBDBG_CMD            = (byte) 48  ;
        private static final byte USBDBG_FRAME_SIZE     = (byte) 0x81;
        private static final byte USBDBG_FRAME_DUMP     = (byte) 0x82;
        private static final byte USBDBG_SCRIPT_EXEC    = (byte) 0x05;
        private static final byte USBDBG_SCRIPT_STOP    = (byte) 0x06;
        private static final byte USBDBG_SCRIPT_RUNNING = (byte) 0x87;
        private static final byte USBDBG_SYS_RESET      = (byte) 0x0C;
        private static final byte USBDBG_FB_ENABLE      = (byte) 0x0D;
        private static final byte USBDBG_TX_BUF_LEN     = (byte) 0x8E;
        private static final byte USBDBG_TX_BUF         = (byte) 0x8F;
        
        public OpenMV(int baudRate, int timeout) {
            connection = new SerialPort(baudRate, SerialPort.Port.kUSB1);
        }

        private void write(byte[] bytes) {
            System.out.println(bytes);
            connection.write(bytes, bytes.length);
        }

        private byte[] read(int numBytes) {
            return connection.read( numBytes );
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

        public int txBufLen() throws Exception {
            write(struct.pack("<BBI", USBDBG_CMD, USBDBG_TX_BUF_LEN, 4));
            return (int)struct.unpack("I", read(4))[0];
        }

        public byte[] txBuf(int numBytes) throws Exception {
            write(struct.pack("<BBI", USBDBG_CMD, USBDBG_TX_BUF, numBytes));
            return read(numBytes);
        }

        public void enableFb(int enable) throws Exception {
            write(struct.pack("<BBI", USBDBG_CMD, USBDBG_FB_ENABLE, 4));
            write(struct.pack("<I", enable));
        }

        public byte[] getSerialOutput() throws Exception {
            return txBuf(txBufLen());
        }

        public byte[] fbDump() throws Exception {
            long[] size = fbSize();
            
            if (size[0] == 0){
                throw new Exception();
            }
            long numBytes = 0;
            if (size[2] > 2){ //JPEG
                System.out.println("Here");
                numBytes = size[2];
            }
            else
                numBytes = size[0]*size[1]*size[2];
            // read fb data
            write(struct.pack("<BBI", (long)USBDBG_CMD, (long)USBDBG_FRAME_DUMP, numBytes));
            byte[] buff = read((int)numBytes);
            return buff;
        }

    }

}
