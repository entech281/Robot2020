package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class HoodHomeCommand extends CommandBase {
    private ShooterSubsystem shoot;

    public HoodHomeCommand(ShooterSubsystem shoot) {
        this.shoot=shoot;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Home called", true);
        shoot.returnToStartPos();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }


}