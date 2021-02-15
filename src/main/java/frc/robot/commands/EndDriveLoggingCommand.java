package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

public class EndDriveLoggingCommand extends EntechCommandBase {

    private DriveSubsystem drive;

    public EndDriveLoggingCommand(DriveSubsystem drive) {
        super(drive);
        this.drive = drive;
    }

    @Override
    public void initialize(){
        drive.endJoystickLogging();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
