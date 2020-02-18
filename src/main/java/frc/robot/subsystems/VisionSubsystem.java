/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.RobotMap;
import frc.robot.posev2.*;
import frc.robot.utils.VisionDataProcessor;
import java.io.StringReader;
import java.util.*;

/**
 *
 * @author aryan
 */
public class VisionSubsystem extends BaseSubsystem{

    private static final int BAUD_RATE = 115200;
    
    private SerialPort visionPort;
    private Scanner scanner;

    String retVal = "";
    String buffer = "";

    private VisionData visionData;
    private VisionDataProcessor formatter;
    
    String[] outputData;

    @Override
    public void initialize() {
        visionPort = new SerialPort(BAUD_RATE, SerialPort.Port.kUSB);        
        formatter = new VisionDataProcessor();
    }

    @Override 
    public void customPeriodic(RobotPose rPose, FieldPose fPose){
        logger.log("ASHDA", "asdas\n asda");
        buffer += visionPort.readString();
        scanner = new Scanner(new StringReader(buffer));
        retVal = scanner.next();
        buffer = formatter.removeFirstInput(buffer);
        logger.log("OpemMV data", retVal);
    }

    public void calculateVisionData(String readings){
        outputData = readings.split(" ");
        boolean visionTargetFound = Boolean.parseBoolean(outputData[0]);

        double lateralOffset = -1;
        double verticalOffset = -1;
        double blobWidth = -1;

        double frameRate = 0.0;

        if(visionTargetFound){
            lateralOffset = Math.abs(RobotMap.ROBOT_DEFAULTS.VISION.FRAME_WIDTH/2 - Integer.parseInt(outputData[1]));
            verticalOffset = Double.parseDouble(outputData[2]);
            blobWidth = Double.parseDouble(outputData[3]);
            frameRate = Double.parseDouble(outputData[4]);
        }
        visionData = new VisionData(visionTargetFound, lateralOffset, verticalOffset, blobWidth);
    }

    public VisionData getVisionData(){
        return visionData;
    }

}
