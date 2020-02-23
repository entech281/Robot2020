package frc.robot.pose;

/**
 *
 * @author dcowden
 */
public class VisionData {

    private double lateralOffset = 0.0;
    private double verticalOffset = 0.0;
    private double targetWidth = 0.0;
    private boolean targetFound = false;

    public VisionData(boolean targetFound, double lateralOffset, double verticalOffset, double targetWidth) {
        this.lateralOffset = lateralOffset;
        this.verticalOffset = verticalOffset;
        this.targetWidth = targetWidth;
        this.targetFound = targetFound;
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
}
