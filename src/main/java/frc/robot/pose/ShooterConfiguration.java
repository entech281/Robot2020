package frc.robot.pose;

    public class ShooterConfiguration {

    private double angle;
    private double wheelSpeed;

    public ShooterConfiguration(double hoodAngle, double wheelSpeedRPM) {
        angle = hoodAngle;
        wheelSpeed = wheelSpeedRPM;
    }

    public double getDesiredHoodAngle() {
        return angle;
    }

    public double getDesiredWheelRPM() {
        return wheelSpeed;
    }
}
