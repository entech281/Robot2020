package frc.robot.posev2;

public class TargetLocation {

    private double distance;
    private double angle;

    public TargetLocation(double distance, double angle) {
        this.distance = distance;
        this.angle = angle;
    }

    public double getDistanceToTarget() {
        return distance / Math.sin(angle);

    }

}
