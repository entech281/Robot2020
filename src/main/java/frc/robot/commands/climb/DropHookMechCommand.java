package frc.robot.commands.climb;

import frc.robot.subsystems.ClimbSubsystem;

public class DropHookMechCommand extends BaseClimbCommand {
    public DropHookMechCommand(ClimbSubsystem climb) {
        super(climb);
    }

    @Override
    public void execute(){
        climb.dropHookRaisingMech();
    }
}