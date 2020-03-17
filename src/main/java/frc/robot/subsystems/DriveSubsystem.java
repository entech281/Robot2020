package frc.robot.subsystems;

import frc.robot.controllers.SparkMaxSettings;
import frc.robot.controllers.SparkMaxSettingsBuilder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotConstants;
import frc.robot.controllers.SparkPositionController;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.pose.*;
import frc.robot.utils.EncoderInchesConverter;
import frc.robot.path.Position;

public class DriveSubsystem extends BaseSubsystem {

    private CANSparkMax frontLeftSpark;
    private CANSparkMax frontRightSpark;
    private CANSparkMax rearLeftSpark;
    private CANSparkMax rearRightSpark;

    private SparkPositionController frontLeftPositionController;
    private SparkPositionController rearLeftPositionController;
    private SparkPositionController frontRightPositionController;
    private SparkPositionController rearRightPositionController;

    private CANEncoder frontLeftEncoder;
    private CANEncoder frontRightEncoder;
    private CANEncoder rearLeftEncoder;
    private CANEncoder rearRightEncoder;

    private SpeedControllerGroup leftSpeedController;
    private SpeedControllerGroup rightSpeedController;
    private DifferentialDrive robotDrive;

    private EncoderInchesConverter encoderConverter = new EncoderInchesConverter(1 / RobotConstants.DIMENSIONS.MOTOR_REVOLUTIONS_PER_INCH);

    
    public static final boolean FRONT_RIGHT_POSITION_INVERSE = true;
    public static final boolean FRONT_LEFT_POSITION_INVERSE = false;
    public static final boolean REAR_RIGHT_POSITION_INVERSE = true;
    public static final boolean REAR_LEFT_POSITION_INVERSE = false;

    private SparkMaxSettings speedSettings = SparkMaxSettingsBuilder.defaults()
            .withCurrentLimits(35)
            .coastInNeutral()
            .withDirections(false, false)
            .noMotorOutputLimits()
            .noMotorStartupRamping()
            .useSpeedControl()
            .build();

    private SparkMaxSettings smartMotionSettings = SparkMaxSettingsBuilder.defaults()
            .withCurrentLimits(35)
            .brakeInNeutral()
            .withDirections(false, false)
            .limitMotorOutputs(1.0, -1.0)
            .withMotorRampUpOnStart(0.1)
            .useSmartPositionControl()
            .withPositionGains(RobotConstants.PID.AUTO_STRAIGHT.F,
                    RobotConstants.PID.AUTO_STRAIGHT.P,
                    RobotConstants.PID.AUTO_STRAIGHT.I,
                    RobotConstants.PID.AUTO_STRAIGHT.D)
            .useAccelerationStrategy(AccelStrategy.kTrapezoidal)
            .withMaxVelocity(RobotConstants.AUTONOMOUS.MAX_VELOCITY)
            .withMaxAcceleration(RobotConstants.AUTONOMOUS.MAX_ACCELLERATION)
            .withClosedLoopError(RobotConstants.AUTONOMOUS.ACCEPTABLE_ERROR)
            .build();

    public void resetPosition() {
        frontLeftPositionController.resetPosition();
        frontRightPositionController.resetPosition();
        rearLeftPositionController.resetPosition();
        rearRightPositionController.resetPosition();        
    }

    
    @Override
    public void initialize() {
        frontLeftSpark = new CANSparkMax(RobotConstants.CAN.FRONT_LEFT_MOTOR, MotorType.kBrushless);
        rearLeftSpark = new CANSparkMax(RobotConstants.CAN.REAR_LEFT_MOTOR, MotorType.kBrushless);
        leftSpeedController = new SpeedControllerGroup(frontLeftSpark, rearLeftSpark);

        frontRightSpark = new CANSparkMax(RobotConstants.CAN.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
        rearRightSpark = new CANSparkMax(RobotConstants.CAN.REAR_RIGHT_MOTOR, MotorType.kBrushless);
        rightSpeedController = new SpeedControllerGroup(frontRightSpark, rearRightSpark);
        frontLeftEncoder = frontLeftSpark.getEncoder();
        frontRightEncoder = frontRightSpark.getEncoder();
        rearLeftEncoder = rearLeftSpark.getEncoder();
        rearRightEncoder = rearRightSpark.getEncoder();
        robotDrive = new DifferentialDrive(leftSpeedController, rightSpeedController);

        frontLeftPositionController = new SparkPositionController(frontLeftSpark, smartMotionSettings, FRONT_LEFT_POSITION_INVERSE);
        frontRightPositionController = new SparkPositionController(frontRightSpark, smartMotionSettings, FRONT_RIGHT_POSITION_INVERSE);
        rearLeftPositionController = new SparkPositionController(rearLeftSpark, smartMotionSettings, REAR_LEFT_POSITION_INVERSE);
        rearRightPositionController = new SparkPositionController(rearRightSpark, smartMotionSettings, REAR_RIGHT_POSITION_INVERSE);
        
        setSpeedMode();
    }

    private void setSpeedMode() {
        speedSettings.configureSparkMax(frontLeftSpark);
        speedSettings.configureSparkMax(frontRightSpark);
        speedSettings.configureSparkMax(rearLeftSpark);
        speedSettings.configureSparkMax(rearRightSpark);
    }

    private void setPositionMode() {
        frontRightPositionController.configure();
        rearLeftPositionController.configure();
        frontLeftPositionController.configure();
        rearRightPositionController.configure();
    }
    
    public EncoderValues getEncoderValues() {
        return new EncoderValues(frontLeftPositionController.getActualPosition(),
                rearLeftPositionController.getActualPosition(),
                frontRightPositionController.getActualPosition(),
                rearRightPositionController.getActualPosition());
    }

    public void stopDriving(){
        robotDrive.tankDrive(0, 0);
    }
    
    public void feedWatchDog(){
        robotDrive.feedWatchdog();
    }
    @Override
    public void periodic() {
        logger.log("Front Left Encoder Ticks", frontLeftEncoder.getPosition());
        logger.log("Front Right Encoder Ticks", frontRightEncoder.getPosition());
        logger.log("Rear Left Encoder Ticks", rearLeftEncoder.getPosition());
        logger.log("Rear Right Encoder Ticks", rearRightEncoder.getPosition());
        logger.log("isSafetyEnabled", robotDrive.isSafetyEnabled());
        if (getCurrentCommand() != null) {
            logger.log("current command", getCurrentCommand().getName());
        }
        logger.log("Output Bus voltage", frontLeftSpark.getBusVoltage());
        logger.log("Applied output", frontLeftSpark.getAppliedOutput());
        logger.log("Output Current", frontLeftSpark.getOutputCurrent());
        logger.log("Faults", frontLeftSpark.getFaults());
        logger.log("Stick Faults", frontLeftSpark.getStickyFaults());
        logger.log("Last error", frontLeftSpark.getLastError());
    }
    
    public void doubleTankDrive(double forwardLeft, double forwardRight ){
        robotDrive.tankDrive(forwardLeft, forwardRight);
    }

    public void curveDrive(double forward, double rotation, boolean fastTurn){
        robotDrive.curvatureDrive(-forward, rotation, fastTurn);
    }
    public void drive(double forward, double rotation) {
        robotDrive.arcadeDrive(forward, rotation);
        
    }
    
    public Position getCurrentPosition(){
        EncoderValues v = getEncoderValues();
        return new Position ( encoderConverter.toInches(v.getLeftFront()),
                              encoderConverter.toInches(v.getRightFront()) );
    }
    
    public double getDistanceTravelled(){
        EncoderValues v = getEncoderValues();
        return encoderConverter.toInches((v.getLeftFront() + v.getRightFront())/2.0);
    }
    
    public void driveToPosition(Position targetPosition){
        setPositionMode();
        double encoderLeft = encoderConverter.toCounts(targetPosition.getLeftInches());
        double encoderRight = encoderConverter.toCounts(targetPosition.getRightInches());

        logger.log("Left Desired", encoderLeft);
        logger.log("Right Desired", encoderRight);    
        
        frontRightPositionController.resetPosition();
        frontLeftPositionController.resetPosition();
        rearRightPositionController.resetPosition();
        rearLeftPositionController.resetPosition();
        
        frontLeftPositionController.setDesiredPosition(encoderLeft);
        frontRightPositionController.setDesiredPosition(encoderRight);
        rearLeftPositionController.setDesiredPosition(encoderLeft);
        rearRightPositionController.setDesiredPosition(encoderRight);
    }
    
    public void switchToBrakeMode(){
        if(frontLeftSpark.getIdleMode() == CANSparkMax.IdleMode.kCoast){
            frontLeftSpark.setIdleMode(CANSparkMax.IdleMode.kBrake);
            frontRightSpark.setIdleMode(CANSparkMax.IdleMode.kBrake);            
            rearLeftSpark.setIdleMode(CANSparkMax.IdleMode.kBrake);
            rearRightSpark.setIdleMode(CANSparkMax.IdleMode.kBrake);
        }
    }
    
    public void switchToCoastMode(){
        if(frontLeftSpark.getIdleMode() == CANSparkMax.IdleMode.kBrake){
            frontLeftSpark.setIdleMode(CANSparkMax.IdleMode.kCoast);
            frontRightSpark.setIdleMode(CANSparkMax.IdleMode.kCoast);            
            rearLeftSpark.setIdleMode(CANSparkMax.IdleMode.kCoast);
            rearRightSpark.setIdleMode(CANSparkMax.IdleMode.kCoast);
        }        
    }

}
