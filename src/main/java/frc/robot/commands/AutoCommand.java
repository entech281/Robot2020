package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoCommand extends ParallelCommandGroup{
    public AutoCommand(ShooterSubsystem shoot){
        addCommands(new HoodHomingCommand(shoot));
    }
}