package frc.robot.posev2;

/**
 *
 * @author dcowden
 */
public class VisionData {

    private double lateralOffset = 0.0;
    private double verticalOffset = 0.0;
    private double targetWidth = 0.0;

    public VisionData(double lateralOffset, double verticalOffset, double targetWidth) {
        this.lateralOffset = lateralOffset;
        this.verticalOffset = verticalOffset;
        this.targetWidth = targetWidth;
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

}
