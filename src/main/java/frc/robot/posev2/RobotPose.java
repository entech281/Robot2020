package frc.robot.posev2;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;


public class RobotPose {
    private double forward = 0.0;
    private double horizontal = 0.0;
    private double theta = 0.0;
    private VisionData targetPosition;
    
    public RobotPose(double f, double h, double t, VisionData target){
        this.forward = f;
        this.horizontal = h;
        this.theta = t;
        this.targetPosition = target;
    }

    public RobotPose(double f, double h, double t){
        this.forward = f;
        this.horizontal = h;
        this.theta = t;        
    }

    public RobotPosition getRobotPosition(){
        return new RobotPosition(forward, horizontal, theta);
    }
    
    public Pose2d getWPIRobotPose() {
        return new Pose2d(forward, horizontal, new Rotation2d(theta));
    }
    
    public double getTargetLateralOffset(){
        return targetPosition.getLateralOffset();
    }
    
    public double getTargetVerticalOffset(){
        return targetPosition.getVerticalOffset();
    }
    
    public WheelColor getCurrentWheelColor(){
        return null;
    }
}