
package frc.robot.commands;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.IntakeSubsystem;

/**
 *
 * @author aryan
 */

public class IntakeOnCommand extends EntechCommandBase{

    private IntakeSubsystem intake;
    private boolean isFinished =false;
    private State currentState = State.I;
    private Timer timer;

    private double DELAY1 = 0.5;
    private double DELAY2 = 0.2;
    public IntakeOnCommand(IntakeSubsystem intake){
        super(intake);
        this.intake = intake;
    }

    @Override
    public void initialize(){
        timer = new Timer();
        setStateI();
    }

    
    
    @Override
    public void execute(){
        if(currentState == State.I){
            if(intake.sensorReadingValue()){
                setStateA();
            }
        } else if(currentState == State.A){
            if(timer.hasElapsed(DELAY1)){
                setStateB();
            }
        } else if(currentState == State.B){
            if(timer.hasElapsed(DELAY2)){
                setStateI();
            }
        }
    }
    
    private void setStateA(){
        timer.start();
        intake.setIntakeMotorSpeed(0.4);
        intake.setElevatorSpeed(0.3);
        currentState = State.A;
    }
    
    public void setStateB(){
        intake.setIntakeMotorSpeed(0);
        intake.setElevatorSpeed(0.5);
        timer.stop();
        timer.reset();
        timer.start();
        currentState = State.B;
    }
    
    public void setStateI(){
        intake.setElevatorSpeed(0);
        intake.setIntakeMotorSpeed(1);
        timer.stop();
        timer.reset();
        currentState = State.I;
    }

    private enum State{
        I,
        A,
        B
    }
}