package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotConstants;
import static frc.robot.RobotConstants.AVAILABILITY.*;
import frc.robot.RobotConstants.MOTORCONTROLLER_VALUES.HOOD_MOTOR;
import frc.robot.RobotConstants.MOTORCONTROLLER_VALUES.SHOOTER_MOTOR;
import frc.robot.commands.SingleShotCommand;
import frc.robot.controllers.*;
import frc.robot.pose.RobotPose;
import frc.robot.pose.ShooterConfiguration;
import frc.robot.utils.ClampedDouble;
import frc.robot.utils.VisionDataProcessor;

public class ShooterSubsystem2 extends BaseSubsystem {


    public static final int RPM_ADJUST_INCREMENT = 150;
    public static final int HOME_OFFSET_COUNT = 75;
    public static final int DEFAULT_SPEED_RPM = 5300;
    public static final int MAX_RPM_SPEED = 5400;
    public static final int MIN_RPM_SPEED = 0;

    private ClampedDouble desiredSpeedRPM = ClampedDouble.builder()
            .bounds(MIN_RPM_SPEED, MAX_RPM_SPEED)
            .withIncrement(RPM_ADJUST_INCREMENT)
            .withValue(DEFAULT_SPEED_RPM).build();

    private ClampedDouble hoodAngleDegrees = ClampedDouble.builder()
            .bounds(0.0, 90.0).withIncrement(1.0).withValue(0.0).build();

    private CANSparkMax shootMotor;
    private SparkSpeedController shooterMotorController;

    private WPI_TalonSRX hoodMotor;
    private TalonPositionController hoodMotorController;

    private boolean homingLimitSwitchHit = false;
    private Timer scheduler = new Timer();

    private boolean autoAdjust = false;
    private boolean preset1 = false; //Right up against target
    private boolean preset2 = false; // 290 inches away
    private boolean shootOn = false;
    private HOOD_MODE hoodMode = HOOD_MODE.OFF;
    private int HOME_OFFSET= 75;
    public enum HOOD_MODE {
        AUTO,
        PRESET1,
        PRESET2,
        OFF
    }

    public Command decreaseRPMSpeedCmd(){
        return new SingleShotCommand(this) {
            @Override
            public void doCommand(){
                System.out.println("Running Inline: decreaseRPMSpeedCmd"  );

                decreaseRPMSpeed();
            }
        };
    }
    
    private VisionDataProcessor processor = new VisionDataProcessor();

    public double getRPMSpeed(){
        return desiredSpeedRPM.getValue();
    }
    public void decreaseRPMSpeed() {
        System.out.println("Decreasing RPM");
        desiredSpeedRPM.decrement();
    }

    public void increaseRPMSpeed() {
        desiredSpeedRPM.increment();
    }

    public void setHoodMode(HOOD_MODE hoodMode) {
        this.hoodMode = hoodMode;
    }

    @Override
    public void initialize() {

        shootMotor = new CANSparkMax(RobotConstants.CAN.SHOOTER_MOTOR, MotorType.kBrushless);
        SparkMaxSettings shooterSettings = SparkMaxSettingsBuilder.defaults()
                .withCurrentLimits(SHOOTER_MOTOR.CURRENT_LIMIT)
                .coastInNeutral()
                .withDirections(false, false)
                .limitMotorOutputs(SHOOTER_MOTOR.SHOOTER_MAXOUTPUT, SHOOTER_MOTOR.SHOOTER_MINOUTPUT)
                .withMotorRampUpOnStart(SHOOTER_MOTOR.SHOOTER_MOTOR_RAMPUP).useSmartMotionControl()
                .withPositionGains(SHOOTER_MOTOR.SHOOTER_PID_F, SHOOTER_MOTOR.SHOOTER_PID_P, SHOOTER_MOTOR.SHOOTER_PID_I, SHOOTER_MOTOR.SHOOTER_PID_D)
                .useAccelerationStrategy(AccelStrategy.kSCurve).withMaxVelocity(SHOOTER_MOTOR.SHOOTER_MAX_RPM)
                .withMaxAcceleration(SHOOTER_MOTOR.SHOOTER_MAX_ACCEL)
                .withClosedLoopError(SHOOTER_MOTOR.SHOOTER_TOLERANCE)
                .build();
        shooterMotorController = new SparkSpeedController(shootMotor, shooterSettings, true);
        shooterMotorController.configure();
        
        hoodMotor = new WPI_TalonSRX(RobotConstants.CAN.HOOD_MOTOR);
        TalonSettings hoodSettings = TalonSettingsBuilder.defaults()
                .withCurrentLimits(1, 1, 1).brakeInNeutral()
                .withDirections(false, false)
                .noMotorOutputLimits()
                .noMotorStartupRamping()
                .usePositionControl()
                .withGains(HOOD_MOTOR.HOOD_PID_F, HOOD_MOTOR.HOOD_PID_P, HOOD_MOTOR.HOOD_PID_I, HOOD_MOTOR.HOOD_PID_D)
                .withMotionProfile(HOOD_MOTOR.HOOD_CRUISE_VELOCITY, HOOD_MOTOR.HOOD_ACCELERATION, HOOD_MOTOR.ALLOWABLE_ERROR)
                .build();

        hoodMotorController = new TalonPositionController(hoodMotor, hoodSettings, false);
        hoodMotorController.configure();
        hoodMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,
                0);
        hoodMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,
                0);

        hoodMotor.overrideLimitSwitchesEnable(true);

    }

    //Current structure of shooter is in auto it will be dictated purely by vision
    //in manual it will be adjusted by alex and with the presets
    @Override
    public void periodic() {
        RobotPose robotPose = getPoseSource().getRobotPose();
        logging(robotPose);
        logger.log("Current command", getCurrentCommand());
        ShooterConfiguration config;
        if (shootOn) {
            if (autoAdjust) {
                if (robotPose.getVisionDataValidity()) {
                    config = processor.calculateShooterConfiguration(robotPose.getTargetLocation());
                    setDesiredShooterConfiguration(config);
                }
            } else {
                if (preset1) {
                    config = processor.calculateShooterConfiguration(RobotConstants.SHOOT_PRESETS.PRESET_1);
                    setDesiredShooterConfiguration(config);
                    preset1 = false;
                } else if (preset2) {
                    config = processor.calculateShooterConfiguration(RobotConstants.SHOOT_PRESETS.PRESET_2);
                    setDesiredShooterConfiguration(config);
                    preset2 = false;
                }
            }

        } else {
            if (shootMotorMounted) {
                shootMotor.stopMotor();
            }
        }
    }

    private void logging(RobotPose rPose) {
        logger.log("TargetLocation", rPose.getTargetLocation().getDistanceToTarget());
        logger.log("Vision Data", rPose.getTargetVerticalOffset());
        if (shootMotorMounted) {
            logger.log("Current Speed", shooterMotorController.getActualSpeed());
        }
        logger.log("Desired Speed", this.desiredSpeedRPM);
    }

    public void adjustShooterSpeed(double desiredSpeed) {
        if (shootMotorMounted) {
            shooterMotorController.setDesiredSpeed(-1 * desiredSpeed);
        }
    }

    public void adjustHoodPosition(double desiredPosition) {
        if (hoodMotorMounted) {
            hoodMotorController.setDesiredPosition(desiredPosition);
        }
    }

    public boolean isUpperLimitHit() {
        if (hoodMotorMounted) {
            return hoodMotor.getSensorCollection().isFwdLimitSwitchClosed();
        }
        return true;
    }

    public boolean isLowerLimitHit() {
        if (shootMotorMounted) {
            return hoodMotor.getSensorCollection().isRevLimitSwitchClosed();
        }
        return true;
    }

    public void shoot() {
        shootOn = true;
    }

    public double getShooterSpeed() {
        if (shootMotorMounted) {
            return shootMotor.getEncoder().getVelocity();
        }
        return 0.0;
    }

    private static class LimitSwitchState {

        public static int closed = 1;
        public static int open = 0;
    }

    public double getHoodPosition() {
        if (hoodMotorMounted) {
            return hoodMotorController.getActualPosition();
        }
        return 0.0;
    }

    public void setDesiredShooterConfiguration(ShooterConfiguration configuration) {

        double desiredPosition = new HoodAngleToEncoderClicksConverter().convertAngleToEncoderClicks(configuration.getDesiredHoodAngle());
        logger.log("Configuration angle", configuration.getDesiredHoodAngle());
        logger.log("encoder clicks", desiredPosition);
        if (hoodMotorMounted) {
            adjustHoodPosition(desiredPosition);
        }
        if (shootMotorMounted) {
            adjustShooterSpeed(5350);
        }
    }

    public class HoodAngleToEncoderClicksConverter {

        public double convertAngleToEncoderClicks(double desiredAngle) {
            return -(((90 - desiredAngle) / 360) * HOOD_MOTOR.ENCODER_CLICKS_PER_HOOD_MOTOR_REVOLUTION * HOOD_MOTOR.HOOD_GEAR_RATIO - HOME_OFFSET);
        }
    }

}
