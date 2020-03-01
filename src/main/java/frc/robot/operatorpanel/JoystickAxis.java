
package frc.robot.operatorpanel;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 *
 * @author dcowden
 */
public class JoystickAxis {
    protected Joystick joystick;
    protected int channel;
    public JoystickAxis ( Joystick joystick, int channel){
        this.joystick = joystick;
        this.channel = channel;

    }
    public double get(){
        return joystick.getRawAxis(channel);
        
    }
}
