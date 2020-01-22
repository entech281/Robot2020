package frc.robot.pose;

public interface PoseGenerator{
    public PositionReader getPose();
    public void updateFromOfficialPose(PositionReader pose);
}