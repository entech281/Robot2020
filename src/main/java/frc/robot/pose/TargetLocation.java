package frc.robot.pose;

public class TargetLocation {

    private double distance;
    private double angle;

    public TargetLocation(double distance, double angle) {
        this.distance = distance;
        this.angle = angle;
    }

    public double getDistanceToTarget() {
        return Math.abs(distance / Math.cos(angle * Math.PI / 180.0));
    }

}
