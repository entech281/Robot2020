package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.DriveSubsystem;

public class TankDriveCommandTwoJoysticks extends EntechCommandBase {

    
    private DriveSubsystem drive;
    private Joystick driveStick1;
    private Joystick driveStick2;
    private JoystickButton tightButton;
    public TankDriveCommandTwoJoysticks(DriveSubsystem drive, Joystick driveStick1, Joystick driveStick2) {
        super(drive);
        this.driveStick1 = driveStick1;
        this.driveStick2 = driveStick2;
        this.drive = drive;
        this.tightButton = tightButton;
    }

    @Override
    public void initialize(){
        drive.switchToCoastMode();
    }
    
    @Override
    public void execute() {
        //drive.drive(-driveStick.getY(), driveStick.getX());
        drive.doubleTankDrive(-driveStick2.getY(), -driveStick1.getY());
    }
    
    @Override
    public boolean isFinished(){
        return false;
    }
}
