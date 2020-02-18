package frc.robot.posev2;

public class TargetLocation {

    private VisionData visionData;
    private double INVALID_ENTRY_RETURN = 102432.024;

    public TargetLocation(VisionData vData) {
        visionData = vData;
    }

    public double getDistanceToTarget() {
        if (visionData == null) {
            return INVALID_ENTRY_RETURN;
        }
        return calculateDistanceToTargetStraight() / Math.sin(calculateAngleToTargetRadians());

    }

    private double calculateAngleToTargetRadians() {
        //TODO: empirically determine relationship
        return 0.0;
    }

    private double calculateDistanceToTargetStraight() {
        //TODO: empirically determine relationship
        return 0.0;
    }

}
