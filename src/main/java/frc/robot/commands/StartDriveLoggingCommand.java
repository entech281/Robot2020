package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

public class StartDriveLoggingCommand extends EntechCommandBase {

    private DriveSubsystem drive;

    public StartDriveLoggingCommand(DriveSubsystem drive) {
        super(drive);
        this.drive = drive;
    }

    @Override
    public void initialize(){
        drive.startJoystickLogging();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
