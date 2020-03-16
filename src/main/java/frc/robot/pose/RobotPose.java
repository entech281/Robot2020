package frc.robot.pose;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import frc.robot.vision.VisionData;
import frc.robot.vision.VisionDataProcessor;

public class RobotPose {

    private RobotPosition robotPosition;
    private VisionData visionData;
    private WheelColorValue wheelColor;
    private TargetLocation targetLocation;
    private boolean visionDataValidity;

    public RobotPose(RobotPosition robotPosition, VisionData vData) {
        this.robotPosition = robotPosition;
        visionData = vData;
        wheelColor = null;
        targetLocation = new VisionDataProcessor().compute(vData, robotPosition.getTheta());
        visionDataValidity = vData.targetFound();

    }

    public RobotPosition getRobotPosition() {
        return robotPosition;
    }

    public Pose2d getWPIRobotPose() {
        return new Pose2d(robotPosition.getForward(), robotPosition.getHorizontal(), new Rotation2d(robotPosition.getTheta()));
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
    
    public boolean getVisionDataValidity(){
        return visionDataValidity;
    }
}
