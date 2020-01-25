package frc.robot.pose;

import frc.robot.RobotMap;
import frc.robot.controllers.SparkPositionControllerGroup;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.logger.DataLogger;
import frc.robot.pose.PoseGenerator;
import frc.robot.pose.RobotPose;
import frc.robot.subsystems.EncoderInchesConverter;
import frc.robot.logger.*;


public class EncoderPoseGenerator implements PoseGenerator{
    private DataLogger logger;

    public final double ENCODER_CLICKS_PER_INCH = RobotMap.DIMENSIONS.ENCODER_TICKS_PER_INCH;
    
    SparkPositionControllerGroup sparkControllers;
    RobotPose pose = RobotMap.DIMENSIONS.START_POSE;
    double positionConfidence = 0.0;
    double thetaConfidence = 0.0;

    double lastLeft;
    double lastRight;

    EncoderInchesConverter converter = new EncoderInchesConverter(ENCODER_CLICKS_PER_INCH);
    
    public EncoderPoseGenerator(SparkPositionControllerGroup group, double positionConfidence, double thetaConfidence){
        this.sparkControllers = group;
        this.positionConfidence = positionConfidence;
        this.thetaConfidence = thetaConfidence;
        this.logger = DataLoggerFactory.getLoggerFactory().createDataLogger("Encoder Pose Genorator");

    }

    public void updatePose(){
        double currentLeft = sparkControllers.getLeftCurrentPosition(converter);
        double currentRight = sparkControllers.getRightCurrentPosition(converter);
        logger.log("currentLeft", currentLeft);
        logger.log("currentRight", currentRight);
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
        this.pose.setForward(pose.getForward());
        this.pose.setTheta(pose.getTheta());
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