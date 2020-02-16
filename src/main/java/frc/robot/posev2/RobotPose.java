package frc.robot.posev2;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;


public class RobotPose {
    private RobotPosition robotPosition;
    private VisionData visionData;
    private Pose2d robotPose2d;
    private WheelColorValue wheelColor;
    private TargetLocation targetLocation;
    
    public RobotPose(double f, double h, double t, VisionData vData){
        robotPosition = new RobotPosition(f, h, t);
        visionData = vData;
        robotPose2d = new Pose2d(f, h, new Rotation2d(t));
        wheelColor = null;
        targetLocation = new TargetLocation(vData);
        }

    public RobotPose(double f, double h, double t){
        robotPose2d = new Pose2d(f, h, new Rotation2d(t));
        robotPosition = new RobotPosition(f, h, t);      
        visionData = null;
        wheelColor = null;
        targetLocation = new TargetLocation(visionData);
    }

    public RobotPosition getRobotPosition(){
        return robotPosition;
    }
    
    public Pose2d getWPIRobotPose() {
        return robotPose2d;
    }
    
    public double getTargetLateralOffset(){
        return visionData.getLateralOffset();
    }
    
    public double getTargetVerticalOffset(){
        return visionData.getVerticalOffset();
    }
    
    public WheelColorValue getCurrentWheelColor(){
        return wheelColor;
    }

    public TargetLocation getTargetLocation(){
        return targetLocation;
    }
}