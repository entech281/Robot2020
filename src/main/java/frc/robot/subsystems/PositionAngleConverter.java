/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.subsystems;

/**
 *
 * @author dcowden
 */
public class PositionAngleConverter {

    public static final int ENCODER_CLICKS_PER_HOOD_MOTOR_REVOLUTION = 4;
    public static final int HOOD_GEAR_RATIO = 4;
    public static final int HOME_OFFSET_COUNTS = 50;
    
    
    public double positionFromAngle(double angle) {
        return -(Math.abs((angle / 360) * ENCODER_CLICKS_PER_HOOD_MOTOR_REVOLUTION * HOOD_GEAR_RATIO - HOME_OFFSET_COUNTS));
    }
    public double angleFromPosition(double position){
        return 0;
    }
    
}
