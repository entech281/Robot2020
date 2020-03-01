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
public class ToggleSwitch {
    protected JoystickButton button;
    public ToggleSwitch( Joystick stick, int port ){
        this.button = new JoystickButton(stick,port);
    }
    public ToggleSwitch( JoystickButton button ){
        this.button = button;
    }
    
    public boolean get(){
        return button.get();
    }
}
