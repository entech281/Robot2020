package frc.robot.pose;

import org.opencv.core.Mat;

/**
 *
 * @author dcowden
 */
public class VisionData {

    private double lateralOffset = 0.0;
    private double verticalOffset = 0.0;
    private double targetWidth = 0.0;
    private boolean targetFound = false;
    private Mat frame;
    public static final VisionData DEFAULT_VISION_DATA = new VisionData(false, -1, -1, -1);


    public VisionData(boolean targetFound, double lateralOffset, double verticalOffset, double targetWidth) {
        this.lateralOffset = lateralOffset;
        this.verticalOffset = verticalOffset;
        this.targetWidth = targetWidth;
        this.targetFound = targetFound;
    }

    public VisionData(boolean targetFound, double lateralOffset, double verticalOffset, double targetWidth, Mat image) {
        this.lateralOffset = lateralOffset;
        this.verticalOffset = verticalOffset;
        this.targetWidth = targetWidth;
        this.targetFound = targetFound;
        this.frame = image;
    }


    public double getLateralOffset() {
        return lateralOffset;
    }

    public double getVerticalOffset() {
        return verticalOffset;
    }

    public double getTargetWidth() {
        return targetWidth;
    }

    public boolean targetFound() {
        return targetFound;
    }

    public Mat getFrame(){
        return frame;
    }
}
