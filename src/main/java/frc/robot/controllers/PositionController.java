/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.controllers;

/**
 *
 * @author dcowden
 */
public interface PositionController {

    double getDesiredPosition();

    /**
     * When you call this, the talon will be put in the right mode for control
     *
     * @param desiredPosition
     */
    void setDesiredPosition(double desiredPosition);
    
}
