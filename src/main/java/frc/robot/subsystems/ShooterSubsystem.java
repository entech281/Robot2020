package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.EntechCommandBase;
import frc.robot.commands.SingleShotCommand;
import frc.robot.controllers.*;
import frc.robot.posev2.FieldPose;
import frc.robot.posev2.RobotPose;
import frc.robot.posev2.ShooterConfiguration;

public class ShooterSubsystem extends BaseSubsystem {

    private double SHOOT_SPEED = 4000;
    private double HOOD_POSITION;

    //Trial and error determination
    private double ENCODER_CLICKS_PER_HOOD_MOTOR_REVOLUTION = 2100;

    private final CANSparkMax shootMotor = new CANSparkMax(5, MotorType.kBrushless);
    private SparkSpeedController shooterMotorController;

    private final WPI_TalonSRX hoodMotor = new WPI_TalonSRX(7);
    private TalonPositionController hoodMotorController;

    private int HOOD_CRUISE_VELOCITY = 3200;
    private int HOOD_ACCELERATION = 20;
    private int ALLOWABLE_ERROR = 5;

    private boolean homingLimitSwitchHit = false;
    private Timer scheduler = new Timer();

    private final double HOOD_PID_F = 4;
    private final double HOOD_PID_P = 2.56 * 2;
    private final double HOOD_PID_I = 0;
    private final double HOOD_PID_D = 0;

    private final double SHOOTER_PID_F = 50;
    private final double SHOOTER_PID_P = 0;
    private final double SHOOTER_PID_I = 0;
    private final double SHOOTER_PID_D = 0;

    private final double RPM = 4000;

    public Command shootRPMSpeed() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                adjustShooterSpeed(SHOOT_SPEED);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }
    
    public Command increaseShooterSpeed() {
        return new SingleShotCommand(this){
            @Override
            public void doCommand(){
                increaseRPM();
            }
        };
    }

    public Command decreaseShooterSpeed() {
        return new SingleShotCommand(this){
            @Override
            public void doCommand(){
                decreaseRPM();
            }
        };
    }
    
    
    public Command stop() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                adjustShooterSpeed(0);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
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
                double desired = hoodMotorController.getActualPosition() - 150;
                adjustHoodPosition(desired);
                while (!(Math.abs(desired - hoodMotorController.getActualPosition()) <= 5)) {

                }
                hoodMotorController.setDesiredPosition(0);
                hoodMotorController.resetPosition();
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    @Override
    public void initialize() {

        SparkMaxSettings shooterSettings = SparkMaxSettingsBuilder.defaults().withCurrentLimits(35).coastInNeutral()
                .withDirections(false, true).noMotorOutputLimits().noMotorStartupRamping().useSpeedControlWithPID()
                .withGainsSpeed(SHOOTER_PID_F, SHOOTER_PID_P, SHOOTER_PID_I, SHOOTER_PID_D).build();

        TalonSettings hoodSettings = TalonSettingsBuilder.defaults().withPrettySafeCurrentLimits().brakeInNeutral()
                .withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping().usePositionControl()
                .withGains(HOOD_PID_F, HOOD_PID_P, HOOD_PID_I, HOOD_PID_D)
                .withMotionProfile(HOOD_CRUISE_VELOCITY, HOOD_ACCELERATION, ALLOWABLE_ERROR).build();

        // Basic outline for shooter
        shooterMotorController = new SparkSpeedController(shootMotor, shooterSettings);

        hoodMotorController = new TalonPositionController(hoodMotor, hoodSettings);
        hoodMotorController.configure();
        hoodMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,
                0);

        hoodMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,
                0);

        hoodMotor.overrideLimitSwitchesEnable(true);
        HOOD_POSITION = hoodMotorController.getActualPosition();
    }

    @Override
    public void customPeriodic(RobotPose rPose, FieldPose fPose) {
        logger.log("Shooter subsystem command", this.getCurrentCommand());
        logger.log("Shooter Motor speed", this.SHOOT_SPEED);
        logger.log("Current Shooter Speed", shootMotor.getEncoder().getVelocity());
    }

    public void adjustShooterSpeed(double desiredSpeed) {
        this.SHOOT_SPEED = desiredSpeed;
        shooterMotorController.setDesiredSpeed(this.SHOOT_SPEED);
    }
    
    public void increaseRPM(){
        if(this.SHOOT_SPEED < 5700){
            this.SHOOT_SPEED += 150;
        }
    }

    public void decreaseRPM(){
        if(this.SHOOT_SPEED > 3500){
            this.SHOOT_SPEED -= 150;
        }
    }
    
    public void adjustHoodPosition(double desiredPosition) {
        this.HOOD_POSITION = desiredPosition;
        hoodMotorController.setDesiredPosition(desiredPosition);
    }

    public boolean isUpperLimitHit() {
        return hoodMotor.getSensorCollection().isFwdLimitSwitchClosed();
    }

    public boolean isLowerLimitHit() {
        return hoodMotor.getSensorCollection().isRevLimitSwitchClosed();
    }

    public void shoot() {
        // There is still uncertainty here
    }

    public double getShooterSpeed() {
        return shootMotor.getEncoder().getVelocity();
    }

    private static class LimitSwitchState {

        public static int closed = 1;
        public static int open = 0;
    }

    public double getHoodPosition() {
        return hoodMotorController.getActualPosition();
    }

    public double getDesiredPositon() {
        return this.HOOD_POSITION;
    }

    public void setDesiredShooterConfiguration(ShooterConfiguration configuration) {
        double desiredPosition = ((90 - configuration.getDesiredHoodAngle()) / 360) * ENCODER_CLICKS_PER_HOOD_MOTOR_REVOLUTION;
        adjustHoodPosition(desiredPosition);
    }

}
