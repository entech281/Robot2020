package frc.robot.pose;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import frc.robot.RobotConstants;
import frc.robot.utils.VisionDataProcessor;

public class RobotPose {

    private RobotPosition robotPosition;
    private VisionData visionData;
    private WheelColorValue wheelColor;
    private TargetLocation targetLocation;

    public RobotPose(RobotPosition robotPosition, VisionData vData) {
        this.robotPosition = robotPosition;
        visionData = vData;
        wheelColor = null;
        targetLocation = new VisionDataProcessor().compute(vData, robotPosition.getTheta());

    }

    public RobotPosition getRobotPosition() {
        return robotPosition;
    }

    public Pose2d getWPIRobotPose() {
        return new Pose2d(robotPosition.getForward()*RobotConstants.RAMSETE.INCHES_TO_METERS, robotPosition.getHorizontal()*RobotConstants.RAMSETE.INCHES_TO_METERS, new Rotation2d(robotPosition.getTheta()-180));
    }

    public double getTargetLateralOffset() {
        return visionData.getLateralOffset();
    }

    public double getTargetVerticalOffset() {
        return visionData.getVerticalOffset();
    }

    public WheelColorValue getCurrentWheelColor() {
        return wheelColor;
    }

    public TargetLocation getTargetLocation() {
        return targetLocation;
    }
}
