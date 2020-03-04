/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.RobotConstants;
import static frc.robot.RobotConstants.CAN.HOOD_MOTOR;
import frc.robot.controllers.TalonPositionController;

/**
 *
 * @author dcowden
 */
public class HoodSubsystem extends BaseSubsystem{

    private WPI_TalonSRX hoodMotor;
    private TalonPositionController hoodMotorController;    
    public static final int HOOD_TOLERANCE = 100;
    public static final int ENCODER_CLICKS_PER_HOOD_MOTOR_REVOLUTION = 4;
    public static final int HOOD_GEAR_RATIO = 4;
    public static final int HOME_OFFSET_COUNTS = 50;
    
    @Override
    public void initialize() {
        hoodMotor = new WPI_TalonSRX(RobotConstants.CAN.HOOD_MOTOR);

        hoodMotorController = new TalonPositionController(hoodMotor, frc.robot.RobotConstants.MOTOR_SETTINGS.HOOD);
        hoodMotorController.configure();
        hoodMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,0);
        hoodMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,0);
        hoodMotor.overrideLimitSwitchesEnable(true);

    }
   public boolean isUpperLimitHit() {
        return hoodMotor.getSensorCollection().isFwdLimitSwitchClosed();
    }

    public boolean isLowerLimitHit() {
        return hoodMotor.getSensorCollection().isRevLimitSwitchClosed();
    }
    
    public void setHoodMotorSpeed(double speed){
        if(!isUpperLimitHit()){
            hoodMotor.set(ControlMode.PercentOutput, speed);
        } else {
            hoodMotor.set(ControlMode.PercentOutput, 0);
        }
    }
    public void setHoodPosition(double desiredPosition) {
        hoodMotorController.setDesiredPosition(desiredPosition);
    }
    public double getHoodPosition(){
        return hoodMotorController.getActualPosition();
    }
    
    public boolean atHoodPosition(){
        return Math.abs(hoodMotorController.getDesiredPosition() - hoodMotorController.getActualPosition()) < HOOD_TOLERANCE;
    }    

    public void adjustHoodForward(){
        //used clamped double
    }
    
    public void adjustHoodBackward(){
        //use clamped double 
    }

    @Override
    public void periodic(){
        logger.log("Hood current position1", hoodMotorController.getActualPosition());
        logger.log("Hood Desired Position1", hoodMotorController.getDesiredPosition());        
    }
    
    private double calculatePositionFromAngle( double angle){
        return -(Math.abs((angle / 360) * ENCODER_CLICKS_PER_HOOD_MOTOR_REVOLUTION * HOOD_GEAR_RATIO - HOME_OFFSET_COUNTS));        
    }
    
    
    private static class LimitSwitchState {

        public static int closed = 1;
        public static int open = 0;
    }
}
