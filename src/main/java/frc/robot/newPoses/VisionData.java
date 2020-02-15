package frc.robot.newPoses;

/**
 *
 * @author dcowden
 */
public class VisionData {

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
    private double lateralOffset  = 0.0;
    private double verticalOffset = 0.0;
}