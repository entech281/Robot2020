package frc.robot.pose;

import java.util.Objects;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

public class RobotPose implements PositionReader{

    private double theta;
    private double horizontal;
    private double lateral;


    public RobotPose(){}
    public RobotPose(double horizontal, double lateral, double theta){
        this.horizontal = horizontal;
        this.lateral = lateral;
        this.theta = theta;
    }

    
    
    public double getTheta() {
        return this.theta;
    }

    
    public void setTheta(double theta) {
        this.theta = theta;
    }

    

    public double getLateral() {
        return this.lateral;
    }

    
    public void setLateral(double lateral) {
        this.lateral = lateral;
    }

    
    public double getHorizontal() {
        return this.horizontal;
    }

    
    public void setHorizontal(double horizontal) {
        this.horizontal = horizontal;
    }

    public Pose2d getWPIRobotPose() {
        return new Pose2d(lateral, horizontal, new Rotation2d(theta));
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RobotPose)) {
            return false;
        }
        RobotPose robotPose = (RobotPose) o;
        return theta == robotPose.theta && horizontal == robotPose.horizontal && lateral == robotPose.lateral;
    }

    @Override
    public int hashCode() {
        return Objects.hash(theta, horizontal, lateral);
    }
    
    @Override
    public String toString() {
        return "{" +
            " theta='" + theta + "'" +
            ", horizontal='" + horizontal + "'" +
            ", lateral='" + lateral + "'" +
            "}";
    }
}