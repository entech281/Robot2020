package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;;

public class StopShooterCommand extends CommandBase {
    private ShooterSubsystem shoot;
    private double desiredSpeed = 0;

    public StopShooterCommand(ShooterSubsystem shoot) {
        this.shoot=shoot;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        shoot.adjustShooterSpeed(desiredSpeed);
        shoot.shoot();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        shoot.shoot();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }


}