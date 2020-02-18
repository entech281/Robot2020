package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ShooterSubsystem;

public class HoodHomingCommand extends SequentialCommandGroup {

    public HoodHomingCommand(ShooterSubsystem shoot) {
        addCommands(shoot.goToUpperLimit().withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS), shoot.returnToStartPos().withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS));
    }
}
