package frc.robot.preferences;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;


public class SmartDashboardPathChooser{

    private SendableChooser<AutoOption> chooser = new SendableChooser<>();

    public SmartDashboardPathChooser(){
        chooser.setDefaultOption("Shoot and back up", AutoOption.ShootAndBackUp);
        chooser.addOption("Shoot and back up", AutoOption.ShootAndBackUp);
        chooser.addOption("Middle 6 ball", AutoOption.MiddleSixBall);
        chooser.addOption("Right 7 ball", AutoOption.RightSevenBall);
    }

    public AutoOption getSelected(){
        return chooser.getSelected();
    }    
}