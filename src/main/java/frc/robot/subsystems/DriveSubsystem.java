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
import frc.robot.path.PositionCalculator;

public class DriveSubsystem extends BaseSubsystem {

    private boolean inAuto;

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
            .useSmartMotionControl()
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
        performInitialization();
        
    }

    private void performInitialization(){
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

    }
    
    public void setSpeedMode() {
        speedSettings.configureSparkMax(frontLeftSpark);
        speedSettings.configureSparkMax(frontRightSpark);
        speedSettings.configureSparkMax(rearLeftSpark);
        speedSettings.configureSparkMax(rearRightSpark);
    }

    public void setPositionMode() {
        frontRightPositionController.configure();
        rearLeftPositionController.configure();
        frontLeftPositionController.configure();
        rearRightPositionController.configure();

        frontRightPositionController.resetPosition();
        frontLeftPositionController.resetPosition();
        rearRightPositionController.resetPosition();
        rearLeftPositionController.resetPosition();
    }
    
    public EncoderValues getEncoderValues() {
            return new EncoderValues(frontLeftEncoder.getPosition(),
                    rearLeftEncoder.getPosition(),
                    frontRightEncoder.getPosition(),
                    rearRightEncoder.getPosition());
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
    }


    public void drive(double forward, double rotation) {
        robotDrive.arcadeDrive(forward, rotation);
    }
    
    public void driveToPosition(double distance){
        setPositionMode();
        Position pose = PositionCalculator.goForward(distance);
        double encoderLeft = encoderConverter.toCounts(pose.getLeftInches());
        double encoderRight = encoderConverter.toCounts(pose.getRightInches());
        
        frontLeftPositionController.setDesiredPosition(encoderLeft);
        frontRightPositionController.setDesiredPosition(encoderRight);
        rearLeftPositionController.setDesiredPosition(encoderLeft);
        rearRightPositionController.setDesiredPosition(encoderRight);
    }


}
