package frc.robot.commands.climb;

import frc.robot.subsystems.ClimbSubsystem;

public class ClimbUpCommand extends BaseClimbCommand {
    public ClimbUpCommand(ClimbSubsystem climb) {
        super(climb);
    }

    @Override
    public void execute(){
        climb.pullRobotUp();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}