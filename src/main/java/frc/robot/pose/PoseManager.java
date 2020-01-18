package frc.robot.pose;

public class PoseManager implements PoseGenerator{
    RobotPose pose;
    RobotPose encoderPose;
    PoseGenerator drivePoseGenerator;
    
    @Override
    public RobotPose getPose(){
        encoderPose = drivePoseGenerator.getPose();
        pose = encoderPose;
        return pose;
    }

}