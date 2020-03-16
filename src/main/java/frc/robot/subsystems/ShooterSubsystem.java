package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import frc.robot.RobotConstants;
import frc.robot.controllers.*;
import frc.robot.utils.ClampedDouble;

public class ShooterSubsystem extends BaseSubsystem {

    public static final int DEFAULT_SHOOTER_FIRE_RPM = 4500;
    public static final int SHOOTER_MAX_RPM = 5400;
    public static final int SHOOTER_RPM_TOLERANCE=100;
    public static final int SHOOTER_RPM_INCREMENT=100;

    private CANSparkMax shootMotor;
    private SparkSpeedController shooterMotorClosedLoopController;  

    private int currentSpeedSetShooter = DEFAULT_SHOOTER_FIRE_RPM;
    public boolean isShooterOn = false;
    
    
    public void startShooterPresetSpeed(){
        isShooterOn = true;
        currentSpeedSetShooter = DEFAULT_SHOOTER_FIRE_RPM;
        shooterMotorClosedLoopController.setDesiredSpeed(DEFAULT_SHOOTER_FIRE_RPM);
    }
    
    public void setShooterSpeed(int currentSpeed){
        currentSpeedSetShooter = currentSpeed;
        shooterMotorClosedLoopController.setDesiredSpeed(currentSpeedSetShooter);
    }
    
    public boolean isShooterOn(){
        return isShooterOn;
    }
    
    public void stopShooter(){
        isShooterOn = false;
        shooterMotorClosedLoopController.stop();
    }
    
    public boolean atShootSpeed(){
        return shooterMotorClosedLoopController.isSpeedWithinTolerance(SHOOTER_RPM_TOLERANCE, currentSpeedSetShooter);
    }
   

    @Override
    public void initialize() {
        shootMotor = new CANSparkMax(RobotConstants.CAN.SHOOTER_MOTOR, MotorType.kBrushless);
        shooterMotorClosedLoopController = new SparkSpeedController(shootMotor, frc.robot.RobotConstants.MOTOR_SETTINGS.SHOOTER_CLOSED_LOOP,true);
        shooterMotorClosedLoopController.configure();
    }

    //Current structure of shooter is in auto it will be dictated purely by vision
    //in manual it will be adjusted by alex and with the presets
    @Override
    public void periodic() {

//        logger.log("Current command", getCurrentCommand());
//        logger.log("Current Speed", shooterMotorClosedLoopController.getActualSpeed());
//        logger.log("Desired Speed", shooterMotorClosedLoopController.getDesiredSpeed());
//        logger.log("AtSpeed", atShootSpeed());
//        logger.log("Enabled", shooterMotorClosedLoopController.isEnabled());
//        logger.log("Start controller config", RobotConstants.MOTOR_SETTINGS.SHOOTER_CLOSED_LOOP.ctrlType);
//        logger.log("Output Bus voltage", shootMotor.getBusVoltage());
//        logger.log("Applied output", shootMotor.getAppliedOutput());
//        logger.log("Output Current", shootMotor.getOutputCurrent());
//        logger.log("Faults", shootMotor.getFaults());
//        logger.log("Stick Faults", shootMotor.getStickyFaults());
//        logger.log("Last error", shootMotor.getLastError());
    }

    
    public double getActualSpeed() {
        return shooterMotorClosedLoopController.getActualSpeed();
    }

    public double getDesiredSpeed(){
        return shooterMotorClosedLoopController.getDesiredSpeed();
    }
}
