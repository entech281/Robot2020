package frc.robot.preferences;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SmartDashboardPathChooser{

    private SendableChooser<AutoOption> chooser = new SendableChooser<>();

    public SmartDashboardPathChooser(){
        chooser.setDefaultOption("Shoot and back up", AutoOption.ShootAndBackUp);
        chooser.addOption("Back up", AutoOption.ShootAndBackUp);
        chooser.addOption("Middle 6 ball", AutoOption.MiddleSixBall);
        chooser.addOption("Left 7 ball", AutoOption.LeftSevenBall);
        SmartDashboard.putData("auto paths", chooser);
    }

    public AutoOption getSelected(){
        return chooser.getSelected();
    }    
}