/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.pose.*;
import frc.robot.vision.VisionDataProcessor;

/**
 *
 * @author aryan
 */
public class VisionSubsystem extends BaseSubsystem {

    private static final int BAUD_RATE = 115200;
    private SerialPort visionPort;

    private VisionData visionData = VisionData.DEFAULT_VISION_DATA;
    private VisionDataProcessor processor;

    private boolean connected = false;

    @Override
    public void initialize() {
        logger.log("initialized", true);
        tryConnect();
        processor = new VisionDataProcessor();
    }

    @Override
    public void periodic() {
        if(connected){
            String reading = visionPort.readString();
            logger.log("Input", reading);
            processor.addInput(reading);
            visionData = processor.getCurrentVisionData();
            logger.log("Vertical offset", visionData.getVerticalOffset());
            logger.driverinfo("Horizontal Offset", visionData.getLateralOffset());
        }
    }

    public VisionData getVisionData() {
        return visionData;
    }

    public boolean ensureConnected(){
        if ( ! connected){
            tryConnect();
        }
        return connected;
    }
    
    private void tryConnect(){
        if ( ! connected){
            
        }
        try{
            visionPort = new SerialPort(BAUD_RATE, SerialPort.Port.kUSB1);
            visionPort.setTimeout(1);
            logger.driverinfo("Vision connection initialization succeded", "SUCCESS");
            connected = true;
        } catch(Exception e){
            logger.driverinfo("Vision connection initialization failed", "FAILED");
        }
    }

}
