package frc.robot.commands.climb;

import frc.robot.subsystems.ClimbSubsystem;

public class AttachHookCommand extends BaseClimbCommand{
    public AttachHookCommand(ClimbSubsystem climb){
        super(climb);
    }

    @Override
    public void execute(){
        climb.raiseHook();
    }
}