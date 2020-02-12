package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class HoodLimitResetCommand extends CommandBase {
    private ShooterSubsystem shoot;

    public HoodLimitResetCommand(ShooterSubsystem shoot) {
        this.shoot=shoot;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        shoot.goToUpperLimit();
        shoot.updateHomingStatus();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        shoot.goToUpperLimit();
        shoot.updateHomingStatus();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return shoot.getLimitSwitchHit();
    }


}