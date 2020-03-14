package frc.robot.subsystems;

import com.fazecast.jSerialComm.SerialPort;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.pose.FieldPose;
import frc.robot.pose.RobotPose;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import static frc.robot.RobotConstants.OPENMV.*;

/**
 *
 * @author dcowden
 */
public class OpenMVCameraFeedSubsystem extends BaseSubsystem{

    private  CvSource source;
    private final Timer timer;
    public static final String SOURCE_NAME = "OpenMVCam";

    private boolean enabled;
    private int counter = 0;
    public OpenMVCameraFeedSubsystem(boolean enabled){
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
           server.setFPS(FRAME_RATE);
           server.setCompression(0);
           server.setDefaultCompression(0);
           server.setResolution(WIDTH, HEIGHT);
           timer.start();            
        }
    }    
    

    public void perdiodic() {
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
        try{
            Mat m = Imgcodecs.imread(FILE_PATH);
            if ( m != null && m.width() == WIDTH && m.height() == HEIGHT){
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