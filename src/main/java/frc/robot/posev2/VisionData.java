package frc.robot.posev2;

/**
 *
 * @author dcowden
 */
public class VisionData {

    private double lateralOffset = 0.0;
    private double verticalOffset = 0.0;
    private double targetWidth = 0.0;

    public VisionData(double lateralOffset, double verticalOffset) {
        this.lateralOffset = lateralOffset;
        this.verticalOffset = verticalOffset;
    }

    public double getLateralOffset() {
        return lateralOffset;
    }

    public void setLateralOffset(double lateralOffset) {
        this.lateralOffset = lateralOffset;
    }

    public double getVerticalOffset() {
        return verticalOffset;
    }

    public void setVerticalOffset(double verticalOffset) {
        this.verticalOffset = verticalOffset;
    }

    public void setTargetWidth(double targetWidth) {
        this.targetWidth = targetWidth;
    }

    public double getTargetWidth() {
        return targetWidth;
    }

}
