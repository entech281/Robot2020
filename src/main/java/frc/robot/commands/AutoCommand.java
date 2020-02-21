package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.path.AutoPathFactory;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SubsystemManager;

public class AutoCommand extends ParallelCommandGroup {

    public AutoCommand(SubsystemManager manager) {
//        addCommands(new HoodHomingCommand(shoot));
        addCommands(new FollowPositionPathCommand(manager.getDriveSubsystem(), AutoPathFactory.getExamplePath()));
    }
}
