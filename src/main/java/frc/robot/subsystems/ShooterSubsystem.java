package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.EntechCommandBase;
import frc.robot.commands.SingleShotCommand;
import frc.robot.controllers.*;
import frc.robot.posev2.FieldPose;
import frc.robot.posev2.RobotPose;
import frc.robot.posev2.ShooterConfiguration;

public class ShooterSubsystem extends BaseSubsystem {

    private double SHOOT_SPEED = 1;
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
    
    private final double SHOOTER_PID_P = 4e-4;
    private final double SHOOTER_PID_I = 32e-7;
    private final double SHOOTER_PID_D = 0;
    private final double SHOOTER_PID_F = 0.000015;
    private final double SHOOTER_MAXOUTPUT = 1;
    private final double SHOOTER_MINOUTPUT = -1;
    private final int CURRENT_LIMIT = 35;
    private final double SHOOTER_MOTOR_RAMPUP = 0.5;
    private final int SHOOTER_MAX_ACCEL = 100;
    private final int SHOOTER_TOLERANCE = 5;
    private final int SHOOTER_MAX_RPM = 5700;
    
    
    private int RPM_SPEED = 4000;
    

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
                RPM_SPEED = 0;
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

    public void decreaseRPMSpeed(){
        if(this.RPM_SPEED > 150){
            this.RPM_SPEED -= 150;
        }
    }

    public void increaseRPMSpeed(){
        if(this.RPM_SPEED < this.SHOOTER_MAX_RPM - 150){
            this.RPM_SPEED += 150;
        }
    }
    
    @Override
    public void initialize() {

        SparkMaxSettings shooterSettings = SparkMaxSettingsBuilder.defaults().withCurrentLimits(this.CURRENT_LIMIT)
                .coastInNeutral().withDirections(false, false).limitMotorOutputs(this.SHOOTER_MAXOUTPUT, this.SHOOTER_MINOUTPUT)
                .withMotorRampUpOnStart(this.SHOOTER_MOTOR_RAMPUP).useSmartMotionControl()
                .withPositionGains(this.SHOOTER_PID_F, this.SHOOTER_PID_P, this.SHOOTER_PID_I, this.SHOOTER_PID_D)
                .useAccelerationStrategy(AccelStrategy.kSCurve).withMaxVelocity(this.SHOOTER_MAX_RPM).withMaxAcceleration(this.SHOOTER_MAX_ACCEL).withClosedLoopError(this.SHOOTER_TOLERANCE).build();
        
        TalonSettings hoodSettings = TalonSettingsBuilder.defaults().withPrettySafeCurrentLimits().brakeInNeutral()
                .withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping().usePositionControl()
                .withGains(HOOD_PID_F, HOOD_PID_P, HOOD_PID_I, HOOD_PID_D)
                .withMotionProfile(HOOD_CRUISE_VELOCITY, HOOD_ACCELERATION, ALLOWABLE_ERROR).build();

        // Basic outline for shooter
        shooterMotorController = new SparkSpeedController(shootMotor, shooterSettings);
        shooterMotorController.configure();
        
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
        logger.log("Current Speed", shooterMotorController.getActualSpeed());
        logger.log("Desired Speed", this.RPM_SPEED);
    }
    
    

    public void adjustShooterSpeed(double desiredSpeed) {
        shootMotor.getPIDController().setReference(-desiredSpeed, ControlType.kVelocity);
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
