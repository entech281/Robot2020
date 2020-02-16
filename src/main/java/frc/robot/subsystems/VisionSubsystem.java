package frc.robot.subsystems;

import java.io.*;
import java.util.Scanner;

import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.posev2.RobotPose;
import frc.robot.posev2.VisionData;
import frc.robot.utils.VisionDataFormatter;
import frc.robot.posev2.FieldPose;

public class VisionSubsystem extends BaseSubsystem{

    private static final int BAUD_RATE = 115200;
    
    private SerialPort visionPort;
    private Scanner scanner;

    String retVal = "";
    String buffer = "";

    private VisionData visionData;
    String[] outputData;

    @Override
    public void initialize() {
        visionPort = new SerialPort(BAUD_RATE, SerialPort.Port.kUSB);        
    }

    @Override 
    public void customPeriodic(RobotPose rPose, FieldPose fPose){
        logger.log("ASHDA", "asdas\n asda");
        buffer += visionPort.readString();
        scanner = new Scanner(new StringReader(buffer));
        retVal = scanner.next();
        buffer = VisionDataFormatter.removeFirstInput(buffer);
        logger.log("OpemMV data", retVal);
    }

    public void calculateVisionData(String readings){
        outputData = readings.split(" ");
        boolean visionTargetFound = Boolean.parseBoolean(outputData[0]);
        if(visionTargetFound){
            int lateralOffset = 

        }
        visionData = new VisionData(lateralOffset, verticalOffset, blobWidth);
    }

}