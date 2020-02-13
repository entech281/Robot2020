package frc.robot.commands.climb;

import frc.robot.subsystems.ClimbSubsystem;

public class EngageClutchCommand extends BaseClimbCommand {
    public EngageClutchCommand(ClimbSubsystem climb) {
        super(climb);
    }

    @Override
    public void execute(){
        climb.engageClutchWithWinch();
    }
}