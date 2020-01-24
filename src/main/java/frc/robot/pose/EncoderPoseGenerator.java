package frc.robot.pose;

import frc.robot.controllers.SparkPositionControllerGroup;
import frc.robot.pose.PoseGenerator;
import frc.robot.pose.RobotPose;
import frc.robot.subsystems.EncoderInchesConverter;

public class EncoderPoseGenerator implements PoseGenerator{
    //TODO: Fix conversion
    public final int ENCODER_CLICKS_PER_INCH = 9;
    
    SparkPositionControllerGroup sparkControllers;
    RobotPose pose;
    double lastLeft;
    double lastRight;

    EncoderInchesConverter converter = new EncoderInchesConverter(ENCODER_CLICKS_PER_INCH);
    
    public EncoderPoseGenerator(SparkPositionControllerGroup group){
        this.sparkControllers = group;
    }

    public void updatePose(){
        double currentLeft = sparkControllers.getLeftCurrentPosition(converter);
        double currentRight = sparkControllers.getRightCurrentPosition(converter);
        double deltaLeft = currentLeft - lastLeft;
        double deltaRight = currentRight - lastRight;
        lastLeft = currentLeft;
        lastRight = currentRight;
        RobotPose deltaPose = PoseMathematics.calculateRobotPositionChange(deltaLeft, deltaRight);
        this.pose = PoseMathematics.addPoses(pose, deltaPose);
    }

    @Override
    public PositionReader getPose() {
        return pose;
    }

    @Override
    public void updateFromOfficialPose(PositionReader pose) {
        this.pose.setHorizontal(pose.getHorizontal());
        this.pose.setLateral(pose.getLateral());
        this.pose.setTheta(pose.getTheta());
    }



}