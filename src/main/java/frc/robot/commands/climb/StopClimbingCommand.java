package frc.robot.commands.climb;

import frc.robot.subsystems.ClimbSubsystem;

public class StopClimbingCommand extends BaseClimbCommand {
    public StopClimbingCommand(ClimbSubsystem climb) {
        super(climb);
    }

    @Override
    public void execute(){
        climb.stopClimbing();
    }
}