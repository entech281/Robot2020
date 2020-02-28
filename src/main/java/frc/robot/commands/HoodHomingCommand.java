package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ShooterSubsystem;

public class HoodHomingCommand extends EntechCommandBase{
    
    ShooterSubsystem shooter;
    private boolean initializationCompleted;
    private State currentState = State.GoToLimitSwitch;
    public HoodHomingCommand(ShooterSubsystem shooter){
        super(shooter);
        this.shooter = shooter;
        initializationCompleted = false;
    }

    @Override
    public boolean isFinished() {
        return initializationCompleted;
    }

    @Override
    public void execute() {
        if(currentState == State.GoToLimitSwitch){
            shooter.setHoodMotorSpeed(0.3);
            if(shooter.isUpperLimitHit()){
                setSettingOffsetState();
            }
        } else if(currentState == State.SettingOffset){
            if(shooter.reachedOffset()){
                setResetState();
            }
        } else if(currentState == State.ResetValues){
            shooter.resetHood();
            initializationCompleted = true;
        }
        
    }
    
    private void setSettingOffsetState(){
        currentState = State.SettingOffset;
        shooter.setHomeOffset();
    }
    
    private void setResetState(){
        currentState = State.ResetValues;
    }

    @Override
    public void initialize() {
    }
    
    

    private enum State{
        GoToLimitSwitch,
        SettingOffset,
        ResetValues
    }
}
