package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DriveInstruction;
import frc.robot.subsystems.DriveSubsystem;

public class TankDriveCommand extends CommandBase {

    @Override
    public boolean isFinished() {
        return super.isFinished(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void execute() {
        drive.drive(new DriveInstruction( -driveStick.getY() , driveStick.getX()));
    }
    
    private DriveSubsystem drive;
    private Joystick driveStick;
    public TankDriveCommand ( DriveSubsystem drive,Joystick driveStick){
        this.driveStick = driveStick;
        this.drive = drive;
    }
}