package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.RobotConstants;
import frc.robot.controllers.*;
import frc.robot.pose.ShooterConfiguration;
import frc.robot.utils.ClampedDouble;

public class ShooterSubsystem extends BaseSubsystem {
    
    public static final int SHOOTER_FIRE_RPM = 5350;
    public static final int SHOOTER_MAX_RPM = 5400;
    public static final int SHOOTER_RPM_TOLERANCE=200;
    public static final int SHOOTER_RPM_INCREMENT=100;

    private CANSparkMax shootMotor;
    private SparkSpeedController shooterMotorClosedLoopController;
    
    private ClampedDouble desiredShooterSpeed = ClampedDouble.builder()
            .bounds(0, SHOOTER_MAX_RPM)
            .withIncrement(SHOOTER_RPM_INCREMENT)
            .withValue(0.0).build();
    
    
    private void update(){
        shooterMotorClosedLoopController.setDesiredSpeed(desiredShooterSpeed.getValue());
    }
    
    public void setShooterSpeedRPM(double desiredSpeed){
        desiredShooterSpeed.setValue(desiredSpeed);
        update();
    }
    
    public void startShooter(){
        setShooterSpeedRPM(SHOOTER_FIRE_RPM);
    }
    
    public void stopShooter(){
        shooterMotorClosedLoopController.stop();
    }
    
    public boolean atShootSpeed(){
        return shooterMotorClosedLoopController.isSpeedWithinTolerance(SHOOTER_RPM_TOLERANCE);
    }
   
    
    public void decreaseRPMSpeed() {
        desiredShooterSpeed.decrement();
        update();
    }

    public void increaseRPMSpeed() {
        desiredShooterSpeed.increment();
        update();      
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

        logger.log("Current command", getCurrentCommand());
        logger.log("Current Speed", shooterMotorClosedLoopController.getActualSpeed());
        logger.log("Desired Speed", shooterMotorClosedLoopController.getDesiredSpeed());
        logger.log("AtSpeed", atShootSpeed());
        logger.log("Enabled", shooterMotorClosedLoopController.isEnabled());
    }

    public double getActualSpeed() {
        return shooterMotorClosedLoopController.getActualSpeed();
    }

    public double getDesiredSpeed(){
        return shooterMotorClosedLoopController.getDesiredSpeed();
    }
}
