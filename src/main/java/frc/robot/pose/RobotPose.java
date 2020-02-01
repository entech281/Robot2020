package frc.robot.pose;

import java.util.Objects;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import frc.robot.RobotMap;

public class RobotPose implements PositionReader{

    private double theta;
    private double horizontal;
    private double forward;


    public RobotPose(){}
    public RobotPose(double horizontal, double forward, double theta){
        this.horizontal = horizontal;
        this.forward = forward;
        this.theta = theta;
        setTheta(theta);
    }

    
    
    public double getTheta() {
        return this.theta;
    }

    
    public void setTheta(double theta) {
        this.theta = theta;
        while(!(this.theta >= -180 && this.theta <= 180)){
            if(theta< -180){
                this.theta = 180 - Math.abs(-180 - this.theta);
            } else {
                this.theta = -180 + Math.abs(180 - this.theta);
            }
        }
        if(this.theta == -180){
            this.theta = 180;
        }
    }


    

    public double getForward() {
        return this.forward;
    }

    
    public void setForward(double forward) {
        this.forward = forward;
    }

    
    public double getHorizontal() {
        return this.horizontal;
    }

    
    public void setHorizontal(double horizontal) {
        this.horizontal = horizontal;
    }

    public Pose2d getWPIRobotPose() {
        return new Pose2d(forward * RobotMap.CONVERSIONS.INCHES_TO_METERS, horizontal* RobotMap.CONVERSIONS.INCHES_TO_METERS, new Rotation2d(theta));
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RobotPose)) {
            return false;
        }
        RobotPose robotPose = (RobotPose) o;
        return theta == robotPose.theta && horizontal == robotPose.horizontal && forward == robotPose.forward;
    }

    @Override
    public int hashCode() {
        return Objects.hash(theta, horizontal, forward);
    }
    
    @Override
    public String toString() {
        return "{" +
            " theta='" + theta + "'" +
            ", horizontal='" + horizontal + "'" +
            ", forward='" + forward + "'" +
            "}";
    }
}