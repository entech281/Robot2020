package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.DriveInstruction;
import frc.robot.DriveInstructionSource;
import frc.robot.RobotMap;
import frc.robot.controllers.PositionDriveController;
import frc.robot.controllers.SparkPositionController;
import frc.robot.controllers.SparkPositionControllerGroup;
import frc.robot.path.PositionBuffer;
import frc.robot.pose.EncoderPoseGenerator;
import frc.robot.subsystems.drive.FourSparkMaxWithSettings;
import frc.robot.pose.PositionReader;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class DriveSubsystem extends BaseSubsystem {

    private static final int ENCODER_TOLERANCE = 50;

    DriveInstructionSource source;
    private boolean inAuto;

    CANSparkMax frontLeftMotor;
    SparkPositionController frontLeftPositionController;
    CANSparkMax rearLeftMotor;
    SparkPositionController rearLeftPositionController;
    SpeedControllerGroup leftSpeedControllerGroup;

    CANSparkMax frontRightMotor;
    SparkPositionController frontRightPositionController;
    CANSparkMax rearRightMotor;
    SparkPositionController rearRightPositionController;
    SpeedControllerGroup rightSpeedController;

    DifferentialDrive robotDrive;

    CANEncoder frontLeftEncoder;
    CANEncoder frontRightEncoder;
    CANEncoder rearLeftEncoder;
    CANEncoder rearRightEncoder;

    private FourSparkMaxWithSettings speedModeSparks;
    private FourSparkMaxWithSettings positionModeSparks;

    private PositionDriveController autoController;

    private SparkPositionControllerGroup posController;

    private EncoderPoseGenerator poseGen;
    private PositionBuffer positionBuffer = new PositionBuffer();

    @Override
    public void initialize() {

        frontLeftMotor = new CANSparkMax(RobotMap.CAN.FRONT_LEFT_MOTOR, MotorType.kBrushless);
        rearLeftMotor = new CANSparkMax(RobotMap.CAN.REAR_LEFT_MOTOR, MotorType.kBrushless);
        leftSpeedControllerGroup = new SpeedControllerGroup(frontLeftMotor, rearLeftMotor);

        frontRightMotor = new CANSparkMax(RobotMap.CAN.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
        rearRightMotor = new CANSparkMax(RobotMap.CAN.REAR_RIGHT_MOTOR, MotorType.kBrushless);
        rightSpeedController = new SpeedControllerGroup(frontRightMotor, rearRightMotor);

        robotDrive = new DifferentialDrive(leftSpeedControllerGroup, rightSpeedController);

        SparkMaxSettings frontLeftSpeedSettings = SparkMaxSettingsBuilder.defaults().withCurrentLimits(35)
                .coastInNeutral().withDirections(false, false).limitMotorOutputs(1.0, -1.0).noMotorStartupRamping()
                .useSpeedControl().build();

        SparkMaxSettings speedSettings = SparkMaxSettingsBuilder.defaults()
                .withCurrentLimits(35)
                .coastInNeutral()
                .withDirections(false, false)
                .limitMotorOutputs(1.0, -1.0)
                .noMotorStartupRamping()
                .useSpeedControl()
                .build();

        speedModeSparks = new FourSparkMaxWithSettings(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor,
                frontLeftSpeedSettings, speedSettings, speedSettings, speedSettings);

        SparkMaxSettings smartMotionSettings = SparkMaxSettingsBuilder.defaults()
                .withCurrentLimits(35)
                .coastInNeutral()
                .withDirections(false, false)
                .limitMotorOutputs(1.0, -1.0)
                .noMotorStartupRamping()
                .useSmartMotionControl()
                .withPositionGains(RobotMap.PID.DRIVE.F, 
                    RobotMap.PID.DRIVE.P, 
                    RobotMap.PID.DRIVE.I, 
                    RobotMap.PID.DRIVE.D)
                .useAccelerationStrategy(AccelStrategy.kTrapezoidal)
                .withMaxVelocity(RobotMap.AUTONOMOUS.MAX_VELOCITY)
                .withMaxAcceleration(RobotMap.AUTONOMOUS.MAX_ACCELLERATION)
                .withClosedLoopError(RobotMap.AUTONOMOUS.ACCEPTABLE_ERROR)
                .build();

        frontLeftPositionController = new SparkPositionController(frontLeftMotor, smartMotionSettings);
        frontLeftPositionController.configure();
        frontRightPositionController = new SparkPositionController(frontRightMotor, smartMotionSettings);
        frontRightPositionController.configure();
        rearLeftPositionController = new SparkPositionController(rearLeftMotor, smartMotionSettings);
        rearLeftPositionController.configure();
        rearRightPositionController = new SparkPositionController(rearRightMotor, smartMotionSettings);
        rearRightPositionController.configure();

        posController = new SparkPositionControllerGroup(frontLeftPositionController, frontRightPositionController,
                rearLeftPositionController, rearRightPositionController);
        positionModeSparks = new FourSparkMaxWithSettings(frontLeftMotor, rearLeftMotor, frontRightMotor,
                rearRightMotor, smartMotionSettings, smartMotionSettings, smartMotionSettings,
                smartMotionSettings);
        positionBuffer = new PositionBuffer();
        autoController = new PositionDriveController(positionModeSparks, positionBuffer,
                new EncoderInchesConverter(RobotMap.DIMENSIONS.MOTOR_REVOLUTIONS_PER_INCH));

        poseGen = new EncoderPoseGenerator(posController);
        frontLeftEncoder = frontLeftMotor.getEncoder();
        frontRightEncoder = frontRightMotor.getEncoder();
        rearLeftEncoder = rearLeftMotor.getEncoder();
        rearRightEncoder = rearRightMotor.getEncoder();

    }

    @Override
    public void periodic() {
        if (inAuto) {
            autoController.periodic();
        }
        logger.log("Front Left Encoder Ticks", frontLeftEncoder.getPosition());
        logger.log("Front Right Encoder Ticks", frontRightEncoder.getPosition());
        logger.log("Rear Left Encoder Ticks", rearLeftEncoder.getPosition());
        logger.log("Rear Right Encoder Ticks", rearRightEncoder.getPosition());
        logger.log("Positions in path", positionBuffer.hasNextPosition());
        if (getCurrentCommand() != null) {
            logger.log("current command", getCurrentCommand().getName());
        }
    }

    public void startAutonomous() {
        inAuto = true;
        robotDrive.setSafetyEnabled(false);
        autoController.activate();
    }

    public void endAutonomous() {
        inAuto = false;
        robotDrive.setSafetyEnabled(true);
    }

    public void reset() {
        frontLeftPositionController.resetPosition();
        frontRightPositionController.resetPosition();
        rearLeftPositionController.resetPosition();
        rearRightPositionController.resetPosition();
        logger.log("Clicks per rotation", rearRightEncoder.getCountsPerRevolution());
    }

    public void updatePose(PositionReader pose) {
        poseGen.updateFromOfficialPose(pose);
        poseGen.updatePose();
    }

    public void drive(DriveInstruction di) {
        if (!inAuto) {
            robotDrive.arcadeDrive(di.getFoward(), di.getRotation());
        }
    }

    public EncoderPoseGenerator getEncoderPoseGenerator() {
        return this.poseGen;
    }

    public PositionBuffer getPositionBuffer() {
        return positionBuffer;
    }
}
