package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.pose.PoseManager;
import frc.robot.subsystems.DriveSubsystem;

public class ResetPositionCommand extends CommandBase {
    private DriveSubsystem robotDrive;
    private PoseManager pm;
    
    public ResetPositionCommand(DriveSubsystem robotDrive, PoseManager pm) {
        this.robotDrive=robotDrive;
        this.pm = pm;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        robotDrive.reset();
        pm.configureRobotPose(0, 0, 90);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        pm.configureRobotPose(0, 0, 90);
        robotDrive.reset();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }


}