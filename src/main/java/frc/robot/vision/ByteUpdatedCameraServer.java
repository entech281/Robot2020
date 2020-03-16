package frc.robot.vision;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;


public class ByteUpdatedCameraServer {
    private DataLogger logger = DataLoggerFactory.getLoggerFactory().createDataLogger("Vision Subsystem Camera Server");

    private CvSource source;
    private final Timer timer;
    public static final String SOURCE_NAME = "OpenMVCam";
    private int width;
    private int height;
    private int frameRate;
    private boolean enabled;
    private int counter = 0;


    public ByteUpdatedCameraServer(boolean enabled) {
        super();
        this.timer = new Timer();
        this.enabled = enabled;
    }

    public void initialize( int width, int height, int fps, int port) {
        source = CameraServer.getInstance().putVideo(SOURCE_NAME, width, height);
        MjpegServer server = CameraServer.getInstance().addServer(SOURCE_NAME, port);
        server.setSource(source);
        server.setFPS(fps);
        server.setCompression(0);
        server.setDefaultCompression(0);
        server.setResolution(width, height);
        this.width = width;
        this.height = height;
        this.frameRate = fps;
        enabled = true;
        timer.start();            
    }    
    
    public void addFrame(byte[] frame) {
        if ( enabled ){
            if ( timer.get() > getDelayBetweenFrames()){
                putFrameFromBytes(frame);
                timer.reset();
            }            
        }
    }    
    
    private double getDelayBetweenFrames(){
        return 1.0 / frameRate;
    }
    
    private void putFrameFromBytes(byte[] frame){  
        try{
            Mat m = Imgcodecs.imdecode(new MatOfByte(frame), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
            if ( m != null && m.width() == width && m.height() == height){
                counter++;
                source.putFrame(m); 
            }
            logger.log("FramesPut",counter);
        }
        catch ( Throwable t){
            logger.warn("Unhandled Error " + t.getMessage());
        }

    }
}