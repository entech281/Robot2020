package frc.robot.path;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubsystem;
import frc.robot.subsystems.SubsystemManager;
import frc.robot.subsystems.VisionSubsystem;
import java.util.ArrayList;
import java.util.List;

public class AutoPathFactory {

    public static List<Position> getExamplePath() {
        return PositionCalculator.builder().left(90).forward(48).left(90).forward(166).right(180).build();
    }
    
    public static List<Position> turn90(){
        return PositionCalculator.builder().forward(24).build();
    }
    
    private DriveSubsystem drive;
    private NavXSubsystem navX;
    private VisionSubsystem vision;
    public AutoPathFactory(SubsystemManager subsystemManager){
        this.drive = subsystemManager.getDriveSubsystem();
        this.navX = subsystemManager.getNavXSubsystem();
        this.vision = subsystemManager.getVisionSubsystem();
    }
    
    public Command[] autoPath1(){
        return AutoPathBuilder.builder(drive, navX, vision).backward(75).right(90).forward(50).right(85).forward(109).backward(75).right(130).snapToTarget().build();
    }
}
