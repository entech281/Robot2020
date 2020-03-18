package frc.robot.controllers;

/**
 *
 * @author plaba
 * @param <State> the class which the solenoid can call set on.
 */
public abstract class StateController<State> {
    private State state;
    public void set(State s){
        this.state = s;
    }
    
    public State get(){
        return state;
    }
}
