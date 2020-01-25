package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class ResetPositionCommand extends CommandBase {
    private DriveSubsystem robotDrive;
    
    public ResetPositionCommand(DriveSubsystem robotDrive) {
        this.robotDrive=robotDrive;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        robotDrive.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        robotDrive.reset();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }


}