package frc.robot.pose;


//The kept pose is at the center of the robot.
public class PoseManager{
    PositionReader pose;
    PositionReader encoderPose;
    PoseGenerator drivePoseGenerator;
    
    public PositionReader getPose(){
        encoderPose = drivePoseGenerator.getPose();
        pose = encoderPose;
        drivePoseGenerator.updateFromOfficialPose(pose);
        return pose;
    }

    public void configureRobotPose(double horizontal, double lateral, double theta){
        pose = new RobotPose(horizontal, lateral, theta);
        drivePoseGenerator.updateFromOfficialPose(pose);
    }

    public void setDrivePoseGenerator(PoseGenerator pg){
        drivePoseGenerator = pg;
    }


}