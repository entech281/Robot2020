package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.DriveSubsystem;

public class TankDriveCommand extends EntechCommandBase {

    private DriveSubsystem drive;
    private Joystick driveStick;
    private JoystickButton tightButton;
    public TankDriveCommand(DriveSubsystem drive, Joystick driveStick) {
        super(drive);
        this.driveStick = driveStick;
        this.drive = drive;
        this.tightButton = tightButton;
    }

    @Override
    public void initialize(){
        drive.switchToCoastMode();
    }
    
    @Override
    public void execute() {
        drive.drive(-driveStick.getY(), driveStick.getX());        
    }
    
    @Override
    public boolean isFinished(){
        return false;
    }
}
