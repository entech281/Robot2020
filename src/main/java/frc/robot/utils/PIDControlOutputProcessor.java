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
public class PIDControlOutputProcessor {
    public static double constrain(double value, double threshold){
        if(value > 0){
                value = Math.min(value, threshold);
        }
        if(value < 0){
                value = Math.max(value, -threshold);
        }
        return value;
    }
    
    public static double constrainWithMinBounds(double value, double maxThresh, double minThresh){
        if(value > 0){
                value = Math.min(value, maxThresh);
                value = Math.max(value, minThresh);
        }
        if(value < 0){
                value = Math.max(value, -maxThresh);
                value = Math.min(value, -minThresh);
        }
        return value;
    }
}
