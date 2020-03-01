/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.operatorpanel;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 *
 * @author dcowden
 */
public class MomentaryButton extends ToggleSwitch{
    
    public MomentaryButton(JoystickButton button) {
        super(button);
    }

    public MomentaryButton(Joystick stick, int port) {
        super(stick, port);
    }
    
}
