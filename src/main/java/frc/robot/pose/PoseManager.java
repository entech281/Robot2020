package frc.robot.pose;


//The kept pose is at the center of the robot.
public class PoseManager implements PoseGenerator{
    PositionReader pose;
    PositionReader encoderPose;
    PoseGenerator drivePoseGenerator;
    
    @Override
    public PositionReader getPose(){
        encoderPose = drivePoseGenerator.getPose();
        pose = encoderPose;
        return pose;
    }

}