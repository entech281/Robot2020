package frc.robot.path;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.CommandGroupFactory;
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

    
    private SubsystemManager subsystemManager;
    private CommandGroupFactory commandFactory;
    
    public AutoPathFactory(SubsystemManager subsystemManager, CommandGroupFactory commandFactory){
        this.subsystemManager = subsystemManager;
        this.commandFactory = commandFactory;
    }
    
    //Start middle shoot 3 balls and pick up 3 more
    public Command[] middleSixBallAuto(){
        return AutoPathBuilder.builder(subsystemManager, commandFactory).zeroYaw(false).backward(75).right(90).forward(50).right(85).forward(109).backward(75).right(130).snapToTarget().build();
    }
    
    public Command[] simplePath(){
        return AutoPathBuilder.builder(subsystemManager, commandFactory).zeroYaw(false).snapToTargetStartShooter().delayForSeconds(0.5).fire().delayForSeconds(3).backward(45).build();
    }

    public Command[] leftEightBallAuto(){
        return AutoPathBuilder.builder(subsystemManager, commandFactory).zeroYaw(true).forward(100).right(135).snapToTargetStartShooter().fire().delayForSeconds(2).nonRelativeTurn(180).forward(100).backward(100).right(135).snapToTargetStartShooter().fire().build();
    }
    
}
