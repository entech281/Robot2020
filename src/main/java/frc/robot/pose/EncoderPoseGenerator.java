package frc.robot.pose;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.Timer;

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
    private double lastUpdated;

    private final double ENCODER_CLICKS_PER_INCH = RobotMap.DIMENSIONS.ENCODER_TICKS_PER_INCH;
    private final double METERS_PER_INCH = RobotMap.DIMENSIONS.INCHES_TO_METERS;
    
    SparkPositionControllerGroup sparkControllers;
    RobotPose pose = RobotMap.DIMENSIONS.START_POSE;
    double lastLeft;
    double lastRight;

    double deltaLeft ;
    double deltaRight;

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
        deltaLeft = currentLeft - lastLeft;
        deltaRight = currentRight - lastRight;
        lastLeft = currentLeft;
        lastRight = currentRight;
        RobotPose deltaPose = PoseMathematics.calculateRobotPositionChange(deltaLeft, deltaRight);
        this.pose = PoseMathematics.addPoses(pose, deltaPose);
        lastUpdated = Timer.getFPGATimestamp();
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

    public DifferentialDriveWheelSpeeds getWheelSpeeds(){
        double deltaT = Timer.getFPGATimestamp() - lastUpdated;
        if(deltaT > 0){
            return new DifferentialDriveWheelSpeeds(deltaLeft * METERS_PER_INCH / deltaT,
                deltaRight * METERS_PER_INCH / deltaT);
        } else{
            return new DifferentialDriveWheelSpeeds(0,0);
        }
    }

}