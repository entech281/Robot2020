package frc.robot.subsystems;

import frc.robot.controllers.PositionDriveController;
import frc.robot.controllers.SparkMaxSettings;
import frc.robot.controllers.SparkMaxSettingsBuilder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import frc.robot.DriveInstruction;
import frc.robot.RobotConstants;
import frc.robot.controllers.SparkPositionController;
import frc.robot.path.PositionBuffer;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.EntechCommandBase;
import frc.robot.commands.SingleShotCommand;
import frc.robot.pose.*;
import frc.robot.utils.EncoderInchesConverter;
import static frc.robot.RobotConstants.AVAILABILITY.*;
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

    private PositionDriveController autoController;

    private PositionBuffer positionBuffer = new PositionBuffer();
    private Pose2d currentpose;

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
            .noMotorStartupRamping()
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

    public Command reset() {
        return new SingleShotCommand(this) {

            @Override
            public void doCommand() {
                frontLeftPositionController.resetPosition();
                frontRightPositionController.resetPosition();
                rearLeftPositionController.resetPosition();
                rearRightPositionController.resetPosition();
                logger.log("Clicks per rotation", rearRightEncoder.getCountsPerRevolution());
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);

    }

    
    @Override
    public void initialize() {
        if (drive) {
            performInitialization();
        }
        reset();
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
            frontLeftPositionController = new SparkPositionController(frontLeftSpark, smartMotionSettings);
            frontRightPositionController = new SparkPositionController(frontRightSpark, smartMotionSettings);
            rearLeftPositionController = new SparkPositionController(rearLeftSpark, smartMotionSettings);
            rearRightPositionController = new SparkPositionController(rearRightSpark, smartMotionSettings);

            autoController = new PositionDriveController(frontRightSpark, rearRightSpark,
                    frontLeftSpark, rearLeftSpark, smartMotionSettings, positionBuffer,
                    new EncoderInchesConverter(1 / RobotConstants.DIMENSIONS.MOTOR_REVOLUTIONS_PER_INCH));

    }
    
    public void setSpeedMode() {
        if (drive) {
            speedSettings.configureSparkMax(frontLeftSpark);
            speedSettings.configureSparkMax(frontRightSpark);
            speedSettings.configureSparkMax(rearLeftSpark);
            speedSettings.configureSparkMax(rearRightSpark);
        }
    }

        public void setPositionMode() {
        if (drive) {
            smartMotionSettings.configureSparkMax(frontLeftSpark);
            smartMotionSettings.configureSparkMax(frontRightSpark);
            smartMotionSettings.configureSparkMax(rearLeftSpark);
            smartMotionSettings.configureSparkMax(rearRightSpark);
        }
    }
    
    public EncoderValues getEncoderValues() {
        if (drive) {
            return new EncoderValues(frontLeftEncoder.getPosition(),
                    rearLeftEncoder.getPosition(),
                    frontRightEncoder.getPosition(),
                    rearRightEncoder.getPosition());
        }
        return new EncoderValues(0, 0, 0, 0);
    }

    @Override
    public void customPeriodic(RobotPose rp, FieldPose fp) {
        if (drive) {
            currentpose = rp.getWPIRobotPose();
            logger.log("Front Left Encoder Ticks", frontLeftEncoder.getPosition());
            logger.log("Front Right Encoder Ticks", frontRightEncoder.getPosition());
            logger.log("Rear Left Encoder Ticks", rearLeftEncoder.getPosition());
            logger.log("Rear Right Encoder Ticks", rearRightEncoder.getPosition());
            logger.log("isSafetyEnabled", robotDrive.isSafetyEnabled());
            if (getCurrentCommand() != null) {
                logger.log("current command", getCurrentCommand().getName());
            }
        }
    }

    public void drive(DriveInstruction di) {
        if (drive) {
            robotDrive.arcadeDrive(di.getFoward(), di.getRotation());
        }
    }

    public void startAutonomous() {
        if (drive) {
            robotDrive.setSafetyEnabled(false);
        }
    }

    public PositionDriveController getAutoController() {
        return autoController;
    }
    
    public void driveToPosition(Position pose){
        positionBuffer.addPosition(pose);
        autoController.activate();
    }

    public void endAutonomous() {
        if (drive) {
            robotDrive.setSafetyEnabled(true);
        }
    }

    public PositionBuffer getPositionBuffer() {
        return positionBuffer;
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        leftSpeedController.setVoltage(leftVolts);
        rightSpeedController.setVoltage(-rightVolts);
        logger.log("leftVolts", leftVolts);
        logger.log("rightVolts", rightVolts);

        robotDrive.feed();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds(){
        double leftSpeed = frontLeftEncoder.getVelocity()
            * RobotConstants.DIMENSIONS.MOTOR_REVOLUTIONS_PER_INCH
            * RobotConstants.RAMSETE.INCHES_TO_METERS;

        double rightSpeed = frontRightEncoder.getVelocity()
            * RobotConstants.DIMENSIONS.MOTOR_REVOLUTIONS_PER_INCH
            * RobotConstants.RAMSETE.INCHES_TO_METERS;
        return new DifferentialDriveWheelSpeeds(leftSpeed, rightSpeed);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
    }

    public Pose2d getCurrentWPIPose(){
        return currentpose;
    }

    public void zeroEncoders(){
        frontLeftEncoder.setPosition(0);
        frontRightEncoder.setPosition(0);
        rearLeftEncoder.setPosition(0);
        rearRightEncoder.setPosition(0);
    };
}