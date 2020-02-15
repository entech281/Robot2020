package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.SingleShotCommand;
import frc.robot.controllers.*;
import frc.robot.newPoses.FieldPose;
import frc.robot.newPoses.RobotPose;

public class ShooterSubsystem extends BaseSubsystem {
    private double SHOOT_SPEED = 1;
    private double HOOD_POSITION;

    private final CANSparkMax shootMotor = new CANSparkMax(5, MotorType.kBrushless);
    private SparkSpeedController shooterMotorController;

    private final WPI_TalonSRX hoodMotor = new WPI_TalonSRX(7);
    private TalonPositionController hoodMotorController;

    private int HOOD_CRUISE_VELOCITY = 3200;
    private int HOOD_ACCELERATION = 20;
    private int ALLOWABLE_ERROR = 5;

    private boolean homingLimitSwitchHit = false;
    private Timer scheduler = new Timer();

    private final double PID_F = 4;
    private final double PID_P = 2.56 * 2;
    private final double PID_I = 0;
    private final double PID_D = 0;

    @Override
    public void initialize() {

        SparkMaxSettings shooterSettings = SparkMaxSettingsBuilder.defaults().withCurrentLimits(35).coastInNeutral()
                .withDirections(false, true).noMotorOutputLimits().noMotorStartupRamping().useSpeedControl().build();
        TalonSettings hoodSettings = TalonSettingsBuilder.defaults().withPrettySafeCurrentLimits().brakeInNeutral()
                .withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping().usePositionControl()
                .withGains(PID_F, PID_P, PID_I, PID_D)
                .withMotionProfile(HOOD_CRUISE_VELOCITY, HOOD_ACCELERATION, ALLOWABLE_ERROR).build();

        // Basic outline for shooter
        shooterSettings.configureSparkMax(shootMotor);

        hoodMotorController = new TalonPositionController(hoodMotor, hoodSettings);
        hoodMotorController.configure();
        hoodMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,
                0);

        hoodMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,
                0);

        hoodMotor.overrideLimitSwitchesEnable(true);
        HOOD_POSITION = hoodMotorController.getActualPosition();
    }

    public void adjustShooterSpeed(double desiredSpeed) {
        logger.log("Intake Motor speed", desiredSpeed);
        this.SHOOT_SPEED = desiredSpeed;
        shootMotor.set(this.SHOOT_SPEED);
    }

    public void adjustHoodPosition(double desiredPosition) {
        this.HOOD_POSITION = desiredPosition;
        hoodMotorController.setDesiredPosition(desiredPosition);
    }


    public Command shootMaxSpeed(){
        return new SingleShotCommand(this){        
            @Override
            public void doCommand() {
                adjustShooterSpeed(1);
            }
        };
    }

    public Command stop(){
        return new SingleShotCommand(this){        
            @Override
            public void doCommand() {
                adjustShooterSpeed(0);
            }
        };
    }

    public Command goToUpperLimit() {
        return new SingleShotCommand(this){
        
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
        };
    }

    public Command returnToStartPos() {
        return new SingleShotCommand(this){        
            @Override
            public void doCommand() {
                double desired = hoodMotorController.getActualPosition() - 150;
                adjustHoodPosition(desired);
                while (!(Math.abs(desired - hoodMotorController.getActualPosition()) <= 5)) {
        
                }
                hoodMotorController.setDesiredPosition(0);
                hoodMotorController.resetPosition();
            }
        };
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

    @Override
    public void customPeriodic(RobotPose rPose, FieldPose fPose) {
        // TODO Auto-generated method stub

    }
} 