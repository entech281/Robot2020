package frc.robot.pose;

import edu.wpi.first.wpilibj.geometry.Pose2d;

//The kept pose is at the center of the robot.
public class PoseManager{

    PositionReader pose;
    PositionReader encoderPose;
    PoseGenerator encoderPoseGenerator;
    
    public PoseManager(PoseGenerator encodeGenerator){
        encoderPoseGenerator = encodeGenerator; 
    }
    
    public PositionReader getPose(){
        encoderPose = encoderPoseGenerator.getPose();
        pose = encoderPose;
        encoderPoseGenerator.updateFromOfficialPose(pose);
        return pose;
    }

    public Pose2d getWPIPose(){
        return pose.getWPIRobotPose();
    }

    public void configureRobotPose(double horizontal, double lateral, double theta){
        pose = new RobotPose(horizontal, lateral, theta);
        encoderPoseGenerator.updateFromOfficialPose(pose);
    }

    public void setEncoderPoseGenerator(PoseGenerator pg){
        encoderPoseGenerator = pg;
    }


}