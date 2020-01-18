package frc.robot.subsystems;

import frc.robot.controllers.SparkPositionControllerGroup;
import frc.robot.pose.PoseGenerator;
import frc.robot.pose.RobotPose;

public class EncoderPoseGenerator implements PoseGenerator{
    RobotPose pose;
    SparkPositionControllerGroup sparkControllers;
    public final int ENCODER_CLICKS_PER_INCH = 9;
    double lastLeft;
    double lastRight;

    //TODO: Fix conversion
    EncoderInchesConverter converter = new EncoderInchesConverter(ENCODER_CLICKS_PER_INCH);
    
    public EncoderPoseGenerator(SparkPositionControllerGroup group){
        this.sparkControllers = group;
    }

    @Override
    public RobotPose getPose(){
        double currentLeft = sparkControllers.getLeftCurrentPosition(converter);
        double currentRight = sparkControllers.getRightCurrentPosition(converter);
        double detlaLeft = currentLeft - lastLeft;
        double deltaRight = currentRight - lastRight;

        return pose;
    }



}