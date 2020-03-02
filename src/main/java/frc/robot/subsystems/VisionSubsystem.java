/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.subsystems;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotConstants;
import frc.robot.pose.*;
import frc.robot.utils.VisionDataProcessor;
import java.io.StringReader;
import java.util.*;

import org.opencv.core.Mat;

/**
 *
 * @author aryan
 */
public class VisionSubsystem extends BaseSubsystem {

    private  CvSource source;
    public static final String SOURCE_NAME = "OpenMVCam";
    
    private static final int BAUD_RATE = 115200;
    private static final int FRAME_RATE = 0;
    private static final int PORT = 0;
    private SerialPort visionPort;

    private VisionData visionData = VisionData.DEFAULT_VISION_DATA;
    private VisionDataProcessor processor;
    private int count = 0;

    @Override
    public void initialize() {
        logger.log("initialized", true);
        try{
            visionPort = new SerialPort(BAUD_RATE, SerialPort.Port.kUSB1);
        } catch(Exception e){
            logger.log("Vision data initialization failed", "FAILED");
        }
        processor = new VisionDataProcessor();

        source = CameraServer.getInstance().putVideo(SOURCE_NAME, RobotConstants.ROBOT_DEFAULTS.VISION.FRAME_WIDTH, RobotConstants.ROBOT_DEFAULTS.VISION.FRAME_HEIGHT);
        MjpegServer server = CameraServer.getInstance().addServer(SOURCE_NAME, PORT);
        server.setSource(source);
        server.setFPS(FRAME_RATE);
        server.setCompression(0);
        server.setDefaultCompression(0);
        server.setResolution(RobotConstants.ROBOT_DEFAULTS.VISION.FRAME_WIDTH, RobotConstants.ROBOT_DEFAULTS.VISION.FRAME_HEIGHT);
    }

    @Override
    public void customPeriodic(RobotPose rPose, FieldPose fPose) {
        String reading = visionPort.readString();
        processor.addInput(reading);
        visionData = processor.getCurrentVisionData();
        logger.log("Vertical offset", visionData.getVerticalOffset());
        logger.log("Horizontal Offset", visionData.getLateralOffset());

        Mat m = visionData.getFrame();

        if ( m != null && m.width() == RobotConstants.ROBOT_DEFAULTS.VISION.FRAME_WIDTH && m.height() == RobotConstants.ROBOT_DEFAULTS.VISION.FRAME_HEIGHT){
            source.putFrame(m);
            logger.log("Frames put", count++);
        }

    }

    public VisionData getVisionData() {
        return visionData;
    }

}
