/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.controllers;

/**
 *
 * @author aryan
 */
public interface SpeedController {

    double getActualSpeed();

    double getDesiredSpeed();

    void setDesiredSpeed(double desiredSpeed);
    
    
    boolean isReversed();
    boolean isEnabled();
    void configure();    
}