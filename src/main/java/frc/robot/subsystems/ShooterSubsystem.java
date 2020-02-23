package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import static frc.robot.RobotConstants.AVAILABILITY.*;
import frc.robot.RobotConstants.MOTORCONTROLLER_VALUES.HOOD_MOTOR;
import frc.robot.RobotConstants.MOTORCONTROLLER_VALUES.SHOOTER_MOTOR;
import frc.robot.commands.EntechCommandBase;
import frc.robot.commands.SingleShotCommand;
import frc.robot.controllers.*;
import frc.robot.pose.FieldPose;
import frc.robot.pose.RobotPose;
import frc.robot.pose.ShooterConfiguration;
import frc.robot.utils.VisionDataProcessor;

public class ShooterSubsystem extends BaseSubsystem {

    private double SHOOT_SPEED = 1;
    private double HOOD_POSITION;

    private CANSparkMax shootMotor;
    private SparkSpeedController shooterMotorController;

    private WPI_TalonSRX hoodMotor;
    private TalonPositionController hoodMotorController;

    private boolean homingLimitSwitchHit = false;
    private Timer scheduler = new Timer();

    private boolean autoAdjust = false;
    private VisionDataProcessor processor = new VisionDataProcessor();

    private int RPM_SPEED = 5350;

    private int HOME_OFFSET= 75;

    public Command shootRPMSpeed() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                adjustShooterSpeed(RPM_SPEED);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command decreaseRPM() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                decreaseRPMSpeed();
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command increaseRPM() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                increaseRPMSpeed();
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command stop() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                adjustShooterSpeed(0);
                shootMotor.stopMotor();
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command enableAutoShooting() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                enableAutoAdjust();
            }
        };
    }

    public Command disableAutoShooting() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                disableAutoAdjust();
            }
        };
    }

    public Command goToUpperLimit() {
        return new SingleShotCommand(this) {

            @Override
            public void doCommand() {
                hoodMotorController.resetPosition();
                hoodMotorController.setDesiredPosition(hoodMotorController.getActualPosition());
                while (!isUpperLimitHit()) {
                    hoodMotor.set(ControlMode.PercentOutput, 0.3);
                    logger.log("POSE", hoodMotorController.getActualPosition());
                }
                hoodMotor.set(ControlMode.Position, hoodMotorController.getActualPosition());
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command returnToStartPos() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                double desired = hoodMotorController.getActualPosition() - HOME_OFFSET;
                adjustHoodPosition(desired);
                while (!(Math.abs(desired - hoodMotorController.getActualPosition()) <= 5)) {

                }
                hoodMotorController.setDesiredPosition(0);
                hoodMotorController.resetPosition();
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public void decreaseRPMSpeed() {
        if (this.RPM_SPEED > 150) {
            this.RPM_SPEED -= 150;
        }
    }

    public void increaseRPMSpeed() {
        if (this.RPM_SPEED < SHOOTER_MOTOR.SHOOTER_MAX_RPM - 150) {
            this.RPM_SPEED += 150;
        }
    }

    public void enableAutoAdjust() {
        autoAdjust = true;
    }

    public void disableAutoAdjust() {
        autoAdjust = false;
    }

    @Override
    public void initialize() {

        if (shootMotorMounted) {
            shootMotor = new CANSparkMax(5, MotorType.kBrushless);
            SparkMaxSettings shooterSettings = SparkMaxSettingsBuilder.defaults().withCurrentLimits(SHOOTER_MOTOR.CURRENT_LIMIT)
                    .coastInNeutral().withDirections(false, false).limitMotorOutputs(SHOOTER_MOTOR.SHOOTER_MAXOUTPUT, SHOOTER_MOTOR.SHOOTER_MINOUTPUT)
                    .withMotorRampUpOnStart(SHOOTER_MOTOR.SHOOTER_MOTOR_RAMPUP).useSmartMotionControl()
                    .withPositionGains(SHOOTER_MOTOR.SHOOTER_PID_F, SHOOTER_MOTOR.SHOOTER_PID_P, SHOOTER_MOTOR.SHOOTER_PID_I, SHOOTER_MOTOR.SHOOTER_PID_D)
                    .useAccelerationStrategy(AccelStrategy.kSCurve).withMaxVelocity(SHOOTER_MOTOR.SHOOTER_MAX_RPM).withMaxAcceleration(SHOOTER_MOTOR.SHOOTER_MAX_ACCEL).withClosedLoopError(SHOOTER_MOTOR.SHOOTER_TOLERANCE).build();
            shooterMotorController = new SparkSpeedController(shootMotor, shooterSettings);
            shooterMotorController.configure();

        }
        if (hoodMotorMounted) {
            hoodMotor = new WPI_TalonSRX(7);
            TalonSettings hoodSettings = TalonSettingsBuilder.defaults().withCurrentLimits(1, 1, 1).brakeInNeutral()
                    .withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping().usePositionControl()
                    .withGains(HOOD_MOTOR.HOOD_PID_F, HOOD_MOTOR.HOOD_PID_P, HOOD_MOTOR.HOOD_PID_I, HOOD_MOTOR.HOOD_PID_D)
                    .withMotionProfile(HOOD_MOTOR.HOOD_CRUISE_VELOCITY, HOOD_MOTOR.HOOD_ACCELERATION, HOOD_MOTOR.ALLOWABLE_ERROR).build();

            hoodMotorController = new TalonPositionController(hoodMotor, hoodSettings);
            hoodMotorController.configure();
            hoodMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,
                    0);
            hoodMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,
                    0);

            hoodMotor.overrideLimitSwitchesEnable(true);
            HOOD_POSITION = hoodMotorController.getActualPosition();
        }
    }

    @Override
    public void customPeriodic(RobotPose rPose, FieldPose fPose) {
        logger.log("TargetLocation", rPose.getTargetLocation().getDistanceToTarget());
        logger.log("Vision Data", rPose.getTargetVerticalOffset());
        if (autoAdjust) {
            ShooterConfiguration config = processor.calculateShooterConfiguration(rPose.getTargetLocation());
            setDesiredShooterConfiguration(config);
        }
        if (shootMotorMounted) {
            logger.log("Current Speed", shooterMotorController.getActualSpeed());
        }
        logger.log("Desired Speed", this.RPM_SPEED);
    }

    public void adjustShooterSpeed(double desiredSpeed) {
        if (shootMotorMounted) {
            shooterMotorController.setDesiredSpeed(-1 * desiredSpeed);
        }
    }

    public void adjustHoodPosition(double desiredPosition) {
        this.HOOD_POSITION = desiredPosition;
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
        // There is still uncertainty here
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

    public double getDesiredPositon() {
        return this.HOOD_POSITION;
    }

    public void setDesiredShooterConfiguration(ShooterConfiguration configuration) {

        double desiredPosition = -(((90 - configuration.getDesiredHoodAngle()) / 360) * HOOD_MOTOR.ENCODER_CLICKS_PER_HOOD_MOTOR_REVOLUTION * HOOD_MOTOR.HOOD_GEAR_RATIO - HOME_OFFSET);
        logger.log("Configuration angle", configuration.getDesiredHoodAngle());
        logger.log("encoder clicks", desiredPosition);
        if (hoodMotorMounted) {
            adjustHoodPosition(desiredPosition);
        }
        if (shootMotorMounted) {
            adjustShooterSpeed(5350);
        }
    }

}
