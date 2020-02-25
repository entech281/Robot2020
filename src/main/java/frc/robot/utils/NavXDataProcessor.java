/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.utils;

/**
 *
 * @author aryan
 */
public class NavXDataProcessor {
    private static boolean inRange(double angle){
        return -180 <= angle && angle <= 180;
    }
    public static double bringInRange(double angle){
        while(!inRange(angle)){
            if(angle < -180){
                angle = 180 - Math.abs(-180 - angle);
            } else {
                angle = -180 + Math.abs(angle - 180);
            }
        }
        return angle;
    }
}
