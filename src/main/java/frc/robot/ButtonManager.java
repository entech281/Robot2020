package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Peter Labadorf
 * 
 * {@summary This class reduces boilerplate button managment code in the 
 * operator interface implementation by building and keeping references 
 * to all the buttons on the joystick.}
 * 
 */
class JoystickButtonManager {

    private Joystick joystick;
    private List<JoystickButton> buttons = new ArrayList<>();

    private JoystickButton buttonBeingBuilt = null;
    
    /**
     * @param joystickPort the port number of the joystick's usb connection 
     */
    public JoystickButtonManager(int joystickPort){
        joystick = new Joystick(joystickPort);
    }

    public JoystickButtonManager(Joystick joystick){
        this.joystick = joystick;
    }

    JoystickButtonManager newButton(int port) throws Exception {
        if(null != buttonBeingBuilt)
            buttonBeingBuilt = new JoystickButton(joystick, port);
        return this;
    }

    JoystickButtonManager whenPressed(Command command) throws Exception {

        if(null != buttonBeingBuilt)
            buttonBeingBuilt.whenPressed(command);
        return this;
    }

    JoystickButtonManager whenReleased(Command command) throws Exception {

        if(null != buttonBeingBuilt)
            buttonBeingBuilt.whenReleased(command);
        return this;
    }   
    
    JoystickButtonManager whileHeld(Command command) throws Exception {

        if(null != buttonBeingBuilt)
            buttonBeingBuilt.whileHeld(command);
        return this;
    } 

    public void add(){
        buttons.add(buttonBeingBuilt);
        buttonBeingBuilt = null;
    }
    
}