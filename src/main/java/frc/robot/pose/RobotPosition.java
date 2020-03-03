package frc.robot.pose;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

public class RobotPosition {

    private double forward;
    private double horizontal;
    private double theta;

    public RobotPosition(double forward, double horizontal, double theta) {
        this.forward = forward;
        this.horizontal = horizontal;
        setTheta(theta);
    }

    private void setTheta(double theta) {
        while (theta < 0) {
            theta = 360 + theta;
        }
        this.theta = theta % 360;
    }

    public RobotPosition() {
        this.forward = 0;
        this.horizontal = 0;
        this.theta = 0;
    }

    public double getForward() {
        return forward;
    }

    public double getHorizontal() {
        return horizontal;
    }

    public double getTheta() {
        return theta;
    }

    public Pose2d getWPIRobotPose() {
        return new Pose2d(getForward(), getHorizontal(), new Rotation2d(getTheta()));
    }
}
