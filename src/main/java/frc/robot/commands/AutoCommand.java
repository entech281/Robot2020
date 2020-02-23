package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.path.AutoPathFactory;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoCommand extends ParallelCommandGroup {

    public AutoCommand(ShooterSubsystem shoot, DriveSubsystem drive, IntakeSubsystem intake) {
        addCommands(
                new FollowPositionPathCommand(drive, AutoPathFactory.getExamplePath()),
                intake.start()
        );
    }
}
