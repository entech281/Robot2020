package frc.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

public class BaseClimbCommand extends CommandBase{
    
    protected ClimbSubsystem climb;

    public BaseClimbCommand(ClimbSubsystem climb){
        this.climb = climb;
    }
    public void initialize() {
        super.initialize();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
    
}