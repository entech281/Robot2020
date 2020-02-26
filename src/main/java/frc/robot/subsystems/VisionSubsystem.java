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
    private int count = 0;

    @Override
    public void initialize() {
        logger.log("initialized", true);

        try{
            visionPort = new SerialPort(BAUD_RATE, SerialPort.Port.kUSB1);
        } catch(Exception e) {
            logger.warn("Couldn't connect to the Villain's Eye.");
        }
        processor = new VisionDataProcessor();
    }

    @Override
    public void customPeriodic(RobotPose rPose, FieldPose fPose) {
        try {
            String reading = visionPort.readString();
            processor.addInput(reading);
            visionData = processor.getCurrentVisionData();
            logger.log("Vertical offset", visionData.getVerticalOffset());
            logger.log("Horizontal Offset", visionData.getLateralOffset());
            
        } catch (Exception e) { }
    }

    public VisionData getVisionData() {
        return visionData;
    }

}
