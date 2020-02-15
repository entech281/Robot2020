package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ShooterSubsystem;

public class HoodHomingCommand extends SequentialCommandGroup{
    public HoodHomingCommand(ShooterSubsystem shoot) {
        addCommands(shoot.goToUpperLimit(), shoot.returnToStartPos());
    }
}