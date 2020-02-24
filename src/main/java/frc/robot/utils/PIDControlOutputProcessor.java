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
                value = Math.min(value, 0.6);
        }
        if(value < 0){
                value = Math.max(value, -0.6);
        }
        return value;
    }
}
