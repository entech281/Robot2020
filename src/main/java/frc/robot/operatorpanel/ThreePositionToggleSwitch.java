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
public class ThreePositionToggleSwitch {
    
    protected JoystickButton firstButton;
    protected JoystickButton secondButton;
    public enum ToggleValue{
        UP,
        MIDDLE,
        DOWN,
    }
    public ThreePositionToggleSwitch(Joystick stick, int port1, int port2){
        this.firstButton = new JoystickButton(stick, port1);
        this.secondButton = new JoystickButton(stick, port2);        
    }
    public ThreePositionToggleSwitch(JoystickButton firstButton, JoystickButton secondButton){
        this.firstButton = firstButton;
        this.secondButton = secondButton;
    }
    
    public ToggleValue get(){
        if ( firstButton.get() ) return ToggleValue.UP;
        if ( secondButton.get()) return ToggleValue.DOWN;
        return ToggleValue.MIDDLE;
    }
    
}
