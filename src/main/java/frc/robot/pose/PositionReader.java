package frc.robot.pose;

import edu.wpi.first.wpilibj.geometry.Pose2d;

public interface PositionReader {

    public double getTheta();

    public double getForward();

    public double getHorizontal();

    public Pose2d getWPIRobotPose();
}
