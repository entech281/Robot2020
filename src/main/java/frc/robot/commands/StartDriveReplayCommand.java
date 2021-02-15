package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

public class StartDriveReplayCommand extends EntechCommandBase {

    private DriveSubsystem drive;

    public StartDriveReplayCommand(DriveSubsystem drive) {
        super(drive);
        this.drive = drive;
    }

    @Override
    public void initialize(){
        drive.startJoystickReplay();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
