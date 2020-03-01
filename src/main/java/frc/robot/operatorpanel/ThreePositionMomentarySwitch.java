/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.operatorpanel;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 *
 * @author dcowden
 */
public class ThreePositionMomentarySwitch extends ThreePositionToggleSwitch{
    
    public ThreePositionMomentarySwitch(JoystickButton firstButton, JoystickButton secondButton) {
        super(firstButton, secondButton);
    }

    public ThreePositionMomentarySwitch(Joystick stick, int port1, int port2) {
        super(stick, port1, port2);
    }
    
}
