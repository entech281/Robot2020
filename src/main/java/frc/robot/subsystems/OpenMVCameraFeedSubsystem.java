
package frc.robot.subsystems;

import edu.wpi.cscore.CvSource;

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
    public OpenMVCameraFeedSubsystem(boolean enabled){
        super();
        this.timer = new Timer();
        this.enabled = enabled;
    }

    @Override
    public void initialize() {
        if ( enabled){
            source = CameraServer.getInstance().putVideo(SOURCE_NAME, WIDTH, HEIGHT);
            timer.start();            
        }

    }    
    
    @Override
    public void customPeriodic(RobotPose rPose, FieldPose fPose) {
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
        Mat m = Imgcodecs.imread(FILE_PATH);
        source.putFrame(m);
    }
    
}
