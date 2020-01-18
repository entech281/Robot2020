package frc.robot.pose;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

public class RobotPose implements PositionWriter, PositionReader, PositionAccessor {

    private double theta;
    private double lateral;
    private double horizontal;

    @Override
    public double getTheta() {
        return this.theta;
    }

    @Override
    public void setTheta(double theta) {
        this.theta = theta;
    }

    @Override

    public double getLateral() {
        return this.lateral;
    }

    @Override
    public void setLateral(double lateral) {
        this.lateral = lateral;
    }

    @Override
    public double getHorizontal() {
        return this.horizontal;
    }

    @Override
    public void setHorizontal(double horizontal) {
        this.horizontal = horizontal;
    }

    public Pose2d getWPIRobotPose() {
        return new Pose2d(lateral, horizontal, new Rotation2d(theta));
    }
}