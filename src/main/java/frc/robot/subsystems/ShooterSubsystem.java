package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.EntechCommandBase;
import frc.robot.commands.SingleShotCommand;
import frc.robot.controllers.*;
import frc.robot.posev2.FieldPose;
import frc.robot.posev2.RobotPose;
import frc.robot.posev2.ShooterConfiguration;

public class ShooterSubsystem extends BaseSubsystem {
    
      private Joystick m_stick;
  private static final int deviceID = 5;
  private CANSparkMax m_motor;
  private CANPIDController m_pidController;
  private CANEncoder m_encoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;

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

    private final double SHOOTER_PID_F = 0;
    private final double SHOOTER_PID_P = 4e-4;
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

    m_stick = new Joystick(0);

    // initialize motor
    m_motor = new CANSparkMax(deviceID, MotorType.kBrushless);

    /**
     * The RestoreFactoryDefaults method can be used to reset the configuration parameters
     * in the SPARK MAX to their factory default state. If no argument is passed, these
     * parameters will not persist between power cycles
     */
    m_motor.restoreFactoryDefaults();

    /**
     * In order to use PID functionality for a controller, a CANPIDController object
     * is constructed by calling the getPIDController() method on an existing
     * CANSparkMax object
     */
    m_pidController = m_motor.getPIDController();

    // Encoder object created to display position values
    m_encoder = m_motor.getEncoder();

    // PID coefficients
    kP = 6e-5; 
    kI = 0;
    kD = 0; 
    kIz = 0; 
    kFF = 0.000015; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = 5700;

    // set PID coefficients
    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);

    // display PID coefficients on SmartDashboard
    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);
    }

    @Override
    public void customPeriodic(RobotPose rPose, FieldPose fPose) {
double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    double iz = SmartDashboard.getNumber("I Zone", 0);
    double ff = SmartDashboard.getNumber("Feed Forward", 0);
    double max = SmartDashboard.getNumber("Max Output", 0);
    double min = SmartDashboard.getNumber("Min Output", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to controller
    if((p != kP)) { m_pidController.setP(p); kP = p; }
    if((i != kI)) { m_pidController.setI(i); kI = i; }
    if((d != kD)) { m_pidController.setD(d); kD = d; }
    if((iz != kIz)) { m_pidController.setIZone(iz); kIz = iz; }
    if((ff != kFF)) { m_pidController.setFF(ff); kFF = ff; }
    if((max != kMaxOutput) || (min != kMinOutput)) { 
      m_pidController.setOutputRange(min, max); 
      kMinOutput = min; kMaxOutput = max; 
    }

    /**
     * PIDController objects are commanded to a set point using the 
     * SetReference() method.
     * 
     * The first parameter is the value of the set point, whose units vary
     * depending on the control type set in the second parameter.
     * 
     * The second parameter is the control type can be set to one of four 
     * parameters:
     *  com.revrobotics.ControlType.kDutyCycle
     *  com.revrobotics.ControlType.kPosition
     *  com.revrobotics.ControlType.kVelocity
     *  com.revrobotics.ControlType.kVoltage
     */
    double setPoint = m_stick.getY()*maxRPM * 3;
    m_pidController.setReference(setPoint, ControlType.kVelocity);
    
    SmartDashboard.putNumber("SetPoint", setPoint);
    SmartDashboard.putNumber("ProcessVariable", m_encoder.getVelocity());
    }

    public void adjustShooterSpeed(double desiredSpeed) {
        this.SHOOT_SPEED = desiredSpeed;
        shootMotor.getPIDController().setReference(this.SHOOT_SPEED, ControlType.kVelocity);
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
