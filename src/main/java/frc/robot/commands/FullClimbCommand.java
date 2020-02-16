package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DelayCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class FullClimbCommand extends SequentialCommandGroup {
    double delayTimeSec = 0.5;
    public FullClimbCommand(ClimbSubsystem climb) {
        addCommands(climb.dropHookRaisingMech().withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS), new DelayCommand(climb, delayTimeSec), climb.pullRobotUp().withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS));
    }
}