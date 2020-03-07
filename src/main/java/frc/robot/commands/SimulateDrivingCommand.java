
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.DriveSubsystem;

/**
 *
 * @author dcowden
 */
public class SimulateDrivingCommand extends EntechCommandBase{
    
    private DriveSubsystem drive;
    private Timer timer;
    private double forward;
    private double lateral;
    private double secondsToDrive;
    public SimulateDrivingCommand(DriveSubsystem drive, double secondsToDrive, double forward, double lateral){
        super(drive);
        this.drive = drive;
        this.forward = forward;
        this.lateral = lateral;
        this.secondsToDrive = secondsToDrive;
        timer = new Timer();
        
    }

    @Override
    public void initialize(){
        timer.start();
    }
    @Override
    public boolean isFinished() {
        return timer.hasElapsed(secondsToDrive);
    }

    @Override
    public void execute() {
        drive.drive(forward, lateral);
    }
}
