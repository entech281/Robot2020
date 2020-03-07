package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.DriveSubsystem;

public class TankDriveCurvatureCommand extends EntechCommandBase {

    private DriveSubsystem drive;
    private Joystick driveStick;
    private JoystickButton tightButton;
    public TankDriveCurvatureCommand(DriveSubsystem drive, Joystick driveStick, JoystickButton tightButton) {
        super(drive);
        this.driveStick = driveStick;
        this.drive = drive;
        this.tightButton = tightButton;
    }

    @Override
    public void initialize(){
        drive.setSpeedMode();
    }
    
    @Override
    public void execute() {
        //drive.drive(-driveStick.getY(), driveStick.getX());
        drive.curveDrive(driveStick.getY(), driveStick.getX(), tightButton.get());
    }
    
    @Override
    public boolean isFinished(){
        return false;
    }
}