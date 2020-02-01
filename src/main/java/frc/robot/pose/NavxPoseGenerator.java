package frc.robot.pose;

import frc.robot.RobotMap;
import frc.robot.subsystems.NavXSubsystem;

public class NavxPoseGenerator implements PoseGenerator{
    private NavXSubsystem navx;

    private double xError;
    private double yError;

    double positionConfidence;
    double thetaConfidence;


    public NavxPoseGenerator(NavXSubsystem navx, double pConf, double tConf){
        this.navx = navx;
        this.positionConfidence = pConf;
        this.thetaConfidence = tConf;
    }

    @Override
    public PositionReader getPose() {
        return new RobotPose(navx.getDisplacementX() * RobotMap.CONVERSIONS.METERS_TO_INCHES + xError, navx.getDisplacementY() *  RobotMap.CONVERSIONS.METERS_TO_INCHES + yError, navx.getAngle());
    }

    @Override
    public void updateFromOfficialPose(PositionReader pose) {
        xError = pose.getHorizontal() - navx.getDisplacementX() * RobotMap.CONVERSIONS.METERS_TO_INCHES ;
        yError = pose.getForward() - navx.getDisplacementY() *  RobotMap.CONVERSIONS.METERS_TO_INCHES ;
    }

    @Override
    public double getPositionConfidence(){
        return positionConfidence;
    }

    @Override
    public double getThetaConfidence(){
        return thetaConfidence;
    }

    @Override
    public void updateConfidences(double newPosConf, double newThetaConf){
        this.positionConfidence = newPosConf;
        this.thetaConfidence = newThetaConf;
    }
}