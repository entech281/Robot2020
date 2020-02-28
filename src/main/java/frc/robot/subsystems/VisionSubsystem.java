/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotConstants;
import frc.robot.pose.*;
import frc.robot.utils.VisionDataProcessor;
import java.io.StringReader;
import java.util.*;

/**
 *
 * @author aryan
 */
public class VisionSubsystem extends BaseSubsystem {

    private static final int BAUD_RATE = 115200;

    
    
    private SerialPort visionPort;

    private VisionData visionData = VisionData.DEFAULT_VISION_DATA;
    private VisionDataProcessor processor;

    private boolean isConnected = false;

    @Override
    public void initialize() {
        logger.log("initialized", true);
        tryConnect();
        processor = new VisionDataProcessor();
    }

    @Override
    public void customPeriodic(RobotPose rPose, FieldPose fPose) {
        if(isConnected){
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

    public void tryConnect(){
        try{
            visionPort = new SerialPort(BAUD_RATE, SerialPort.Port.kUSB1);
            visionPort.setTimeout(1);
            logger.driverinfo("Vision connection initialization succeded", "SUCCESS");
            isConnected = true;
        } catch(Exception e){
            logger.driverinfo("Vision connection initialization failed", "FAILED");
        }
    }

    public boolean isConnected(){
        return isConnected;
    }

}
