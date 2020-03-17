/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort.Port;
import frc.robot.vision.VisionData;
import frc.robot.vision.WpilibSerialProvider;
import frc.robot.vision.ByteUpdatedCameraServer;
import frc.robot.vision.SerialProvider;
import frc.robot.vision.VisionCommManager;

import frc.robot.RobotConstants.DEFAULTS.VISION;

/**
 *
 * @author aryan,plaba
 */
public class VisionSubsystem extends BaseSubsystem {

    private static final int BAUD_RATE = 115200;
    private static final int TIMEOUT = 1;
    private static final Port PORT = Port.kUSB1;
    private SerialProvider connection;
    private VisionCommManager communication;
    private ByteUpdatedCameraServer visionStream;

    private boolean connected = false;

    @Override
    public void initialize() {
        logger.log("initialized", true);
        visionStream = new ByteUpdatedCameraServer(false);
        tryConnect();
    }

    @Override
    public void periodic() {
        if(connected){
            communication.update();
            visionStream.addFrame(communication.getLatestImage());
        }
    }

    public VisionData getVisionData() {
        return communication.getLatestTargetData();
    }

    public byte[] getImageData(){
        return communication.getLatestImage();
    }

    public boolean ensureConnected(){
        if ( ! connected){
            tryConnect();
        }
        return connected;
    }
    
    private void tryConnect(){
        if ( ! connected){
            try{
                connection = new WpilibSerialProvider(BAUD_RATE, TIMEOUT, PORT);
                communication = new VisionCommManager(connection);
                visionStream.initialize(VISION.FRAME_HEIGHT, VISION.FRAME_WIDTH, VISION.STREAM_FPS, VISION.STREAM_PORT);
                logger.driverinfo("Vision connection initialization", "SUCCESS");
                connected = true;
            } catch(Exception e){
                logger.driverinfo("Vision connection initialization", "FAILED");
            }
        }            
    }

}
