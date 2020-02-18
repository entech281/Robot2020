package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.DriveInstruction;
import frc.robot.subsystems.DriveSubsystem;

public class TankDriveCommand extends EntechCommandBase {

    private DriveSubsystem drive;
    private Joystick driveStick;

    public TankDriveCommand(DriveSubsystem drive, Joystick driveStick) {
        super(drive);
        this.driveStick = driveStick;
        this.drive = drive;
    }

    @Override
    public void execute() {
        drive.drive(new DriveInstruction(-driveStick.getY(), driveStick.getX()));
    }
}
