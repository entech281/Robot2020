package frc.robot.posev2;

/**
 *
 * @author dcowden
 */
public class VisionData {
    private double lateralOffset  = 0.0;
    private double verticalOffset = 0.0;
    private double targetWidth = 0.0;
    private boolean validData = true;

    public VisionData(boolean validity, double lateralOffset, double verticalOffset, double targetWidth){
        this.lateralOffset = lateralOffset;
        this.verticalOffset = verticalOffset;
        this.targetWidth = targetWidth;
        this.validData = validity;
    }

    public double getLateralOffset() {
        return lateralOffset;
    }

    public double getVerticalOffset() {
        return verticalOffset;
    }

    public double getTargetWidth(){
        return targetWidth;
    }

    public boolean dataValidity(){
        return validData;
    }

}