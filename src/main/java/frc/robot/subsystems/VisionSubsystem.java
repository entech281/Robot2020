/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.RobotConstants;
import frc.robot.pose.*;
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


    private VisionData visionData;
    private VisionDataProcessor processor;
    

    @Override
    public void initialize() {
        visionPort = new SerialPort(BAUD_RATE, SerialPort.Port.kUSB);        
        processor = new VisionDataProcessor();
    }

    @Override 
    public void customPeriodic(RobotPose rPose, FieldPose fPose){
        processor.addInput(visionPort.readString());
        visionData = processor.getCurrentVisionData();
    }



    public VisionData getVisionData(){
        return visionData;
    }

}
