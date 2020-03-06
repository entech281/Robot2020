/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.subsystems;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author dcowden
 */
public class OpenMVCameraSubsystem extends BaseSubsystem{
    private  CvSource source;
    private final Timer timer;
    public static final String SOURCE_NAME = "OpenMVCam";
    private boolean enabled;
    private int counter = 0;
    public static final int FRAME_RATE=20;
    public static final int WIDTH=240;
    public static final int HEIGHT=320;
    public static final int PORT = 80;
    public OpenMVCameraSubsystem(boolean enabled){
        super();
        this.timer = new Timer();
        this.enabled = enabled;
    }
    @Override
    public void initialize() {
        if ( enabled){
           source = CameraServer.getInstance().putVideo(SOURCE_NAME, WIDTH, HEIGHT);
           MjpegServer server = CameraServer.getInstance().addServer(SOURCE_NAME, PORT);
           server.setSource(source);
           server.setFPS(20);
           server.setCompression(0);
           server.setDefaultCompression(0);
           server.setResolution(320, 240);
           timer.start();
        }
    }
    @Override
    public void periodic() {
        if ( enabled ){
            if ( timer.get() > getDelayBetweenFrames()){
                putFrameFromFile();
                timer.reset();
            }
        }
    }
    private double getDelayBetweenFrames(){
        return 1.0 / FRAME_RATE;
    }
    private void putFrameFromFile(){
//        try{
//            Mat m = Imgcodecs.imread(FILE_PATH);
//            if ( m != null && m.width() == WIDTH && m.height() == HEIGHT){
//                counter++;
//                source.putFrame(m);
//            }
//            logger.log("FramesPut",counter);
//        }
//        catch ( Throwable t){
//            logger.warn("Unhandled Error " + t.getMessage());
//        }
    }
}
