package frc.robot.controllers.digitialinput;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author plaba
 */
public class RealDigitalInput extends BaseDigitalInput{
    private DigitalInput input;
    
    public RealDigitalInput(int portID){
        this.input = new DigitalInput(portID);
    }
    
    @Override 
    public Boolean get(){
        return input.get();
    }
    
    @Override 
    public void set(Boolean b){
        throw new UnsupportedOperationException("Cannot set digitial input while not in test mode!");
    }
}
