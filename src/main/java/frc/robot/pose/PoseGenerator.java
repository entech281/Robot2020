package frc.robot.pose;

public interface PoseGenerator{
    public PositionReader getPose();
    public void updateFromOfficialPose(PositionReader pose);
    public double getPositionConfidence();
    public double getThetaConfidence();
    public void updateConfidences(double newPosConf, double newThetaConf);
}