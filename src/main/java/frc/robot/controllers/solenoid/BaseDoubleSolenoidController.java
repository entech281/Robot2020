package frc.robot.controllers.solenoid;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.controllers.StateController;

public class BaseDoubleSolenoidController extends StateController<Value>{
    
    public void set(Value v){
        super.set(v);
    }
    public Value get(){
        return super.get();
    }
}
