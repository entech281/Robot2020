package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Peter Labadorf
 * 
 * {@summary This class reduces boilerplate button managment code in the operator interface implementation by building and keeping references to all the buttons on the joystick.}
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
        else 
            throw new Exception("You called newButton when you were already building a button!!1");
        return this;
    }

    JoystickButtonManager whenPressed(Command command) throws Exception {

        if(null == buttonBeingBuilt)
            throw new Exception("You called whenPressed when you were not already building a button!!1");
        buttonBeingBuilt.whenPressed(command);
        return this;
    }

    JoystickButtonManager whenReleased(Command command) throws Exception {

        if(null == buttonBeingBuilt)
            throw new Exception("You called whenReleased when you were not already building a button!!1");
        buttonBeingBuilt.whenReleased(command);
        return this;
    }   
    
    JoystickButtonManager whileHeld(Command command) throws Exception {

        if(null == buttonBeingBuilt)
            throw new Exception("You called whileHeld when you were not already building a button!!1");
        buttonBeingBuilt.whileHeld(command);
        return this;
    } 

    public void add(){
        buttons.add(buttonBeingBuilt);
        buttonBeingBuilt = null;
    }
    
}