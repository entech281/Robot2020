package frc.robot.pose;

import frc.robot.subsystems.NavXSubsystem;

public class NaxvPoseGenerator implements PoseGenerator{
    private NavXSubsystem navx;

    private double xError;
    private double yError;


    public NaxvPoseGenerator(NavXSubsystem navx){
        this.navx = navx;
    }

    @Override
    public PositionReader getPose() {
        return new RobotPose(navx.getDisplacementX() + xError, navx.getDisplacementY() + yError, navx.getAngle());
    }

    @Override
    public void updateFromOfficialPose(PositionReader pose) {
        xError = pose.getHorizontal() - navx.getDisplacementX();
        yError = pose.getForward() - navx.getDisplacementY();
    }
}