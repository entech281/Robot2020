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
    //TODO: Fix conversion
    private DataLogger logger;

    public final double ENCODER_CLICKS_PER_INCH = RobotMap.DIMENSIONS.ENCODER_TICKS_PER_INCH;
    
    SparkPositionControllerGroup sparkControllers;
    RobotPose pose = RobotMap.DIMENSIONS.START_POSE;
    double lastLeft;
    double lastRight;

    EncoderInchesConverter converter = new EncoderInchesConverter(ENCODER_CLICKS_PER_INCH);
    
    public EncoderPoseGenerator(SparkPositionControllerGroup group){
        this.sparkControllers = group;
        
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
        this.pose.setLateral(pose.getLateral());
        this.pose.setTheta(pose.getTheta());
    }



}