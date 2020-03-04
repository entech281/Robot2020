package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.RobotConstants;
import frc.robot.controllers.*;
import frc.robot.pose.ShooterConfiguration;

public class ShooterSubsystem extends BaseSubsystem {

    public static final int SHOOTER_FIRE_RPM = 5350;
    public static final int SHOOTER_RPM_TOLERANCE=100;
    public static final int SHOOTER_RPM_INCREMENT=100;

    private CANSparkMax shootMotor;
    private SparkSpeedController shooterMotorController;

    
    public void setShooterSpeedRPM(double desiredSpeed){
        shooterMotorController.setDesiredSpeed(desiredSpeed);
    }
    
    public void startShooter(){
        setShooterSpeedRPM(SHOOTER_FIRE_RPM);
    }
    public void stopShooter(){
        setShooterSpeedRPM(0.0);
    }
    
    public boolean atShootSpeed(){
        return shooterMotorController.isSpeedWithinTolerance(SHOOTER_RPM_TOLERANCE);
    }
   
    
    public void decreaseRPMSpeed() {
        //use clamped double
    }

    public void increaseRPMSpeed() {
        //use clamped double        
    }

    @Override
    public void initialize() {
        shootMotor = new CANSparkMax(RobotConstants.CAN.SHOOTER_MOTOR, MotorType.kBrushless);
        shooterMotorController = new SparkSpeedController(shootMotor, frc.robot.RobotConstants.MOTOR_SETTINGS.SHOOTER);
        shooterMotorController.configure();
    }

    //Current structure of shooter is in auto it will be dictated purely by vision
    //in manual it will be adjusted by alex and with the presets
    @Override
    public void periodic() {

        logger.log("Current command", getCurrentCommand());
        ShooterConfiguration config;
        logger.log("Current Speed", shooterMotorController.getActualSpeed());
        logger.log("Desired Speed", shooterMotorController.getDesiredSpeed());
        logger.log("AtSpeed", atShootSpeed());

    }


    public double getActualSpeed() {
        return shooterMotorController.getActualSpeed();
    }

    public double getDesiredSpeed(){
        return shooterMotorController.getDesiredSpeed();
    }
}
