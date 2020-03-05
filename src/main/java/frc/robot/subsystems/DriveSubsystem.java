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
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.EntechCommandBase;
import frc.robot.commands.SingleShotCommand;
import frc.robot.pose.*;
import frc.robot.utils.EncoderInchesConverter;
import frc.robot.path.Position;
import frc.robot.path.PositionCalculator;
import java.util.ArrayList;

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
     
    public static final boolean frontRightPositionInverse = true;
    public static final boolean frontLeftPositionInverse = false;
    public static final boolean rearRightPositionInverse = true;
    public static final boolean rearLeftPositionInverse = false;

    
    private ArrayList<SparkPositionController> positionControllers = new ArrayList<>();

    
    private RobotPose latestRobotPose = RobotConstants.ROBOT_DEFAULTS.START_POSE;


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
        performInitialization();
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
            frontLeftPositionController = new SparkPositionController(frontLeftSpark, smartMotionSettings, frontLeftPositionInverse);
            frontRightPositionController = new SparkPositionController(frontRightSpark, smartMotionSettings, frontRightPositionInverse);
            rearLeftPositionController = new SparkPositionController(rearLeftSpark, smartMotionSettings, rearLeftPositionInverse);
            rearRightPositionController = new SparkPositionController(rearRightSpark, smartMotionSettings, rearRightPositionInverse);
            
            positionControllers.add(frontLeftPositionController);
            positionControllers.add(frontRightPositionController);
            positionControllers.add(rearLeftPositionController);
            positionControllers.add(rearRightPositionController);

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
    public void customPeriodic(RobotPose rp, FieldPose fp) {
            logger.log("Front Left Encoder Ticks", frontLeftEncoder.getPosition());
            logger.log("Front Right Encoder Ticks", frontRightEncoder.getPosition());
            logger.log("Rear Left Encoder Ticks", rearLeftEncoder.getPosition());
            logger.log("Rear Right Encoder Ticks", rearRightEncoder.getPosition());
            logger.log("isSafetyEnabled", robotDrive.isSafetyEnabled());
            if (getCurrentCommand() != null) {
                logger.log("current command", getCurrentCommand().getName());
            }
        latestRobotPose = rp;
    }
    
    public RobotPose getLatestRobotPose(){
        return latestRobotPose;
    }

    public void drive(double forward, double rotation) {
        robotDrive.arcadeDrive(forward, rotation);
    }

    public void startAutonomous() {
        inAuto = true;
        robotDrive.setSafetyEnabled(false);
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

    public void endAutonomous() {
        robotDrive.setSafetyEnabled(true);
    }
}
