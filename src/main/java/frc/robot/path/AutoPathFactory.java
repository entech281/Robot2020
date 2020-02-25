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
    
    //Start middle shoot 3 balls and pick up 3 more (8 seconds)
    public Command[] middleSixBallAuto(){
        return AutoPathBuilder.builder(subsystemManager, commandFactory, false).zeroYaw().startShooterAndHomeHood().next().delayForSeconds(0.5).enableAutoShooterHoodAdjustment().next().fire().delayForSeconds(3).next().backward(75).startIntake().stopJustSpinningShooterWheel().next().right(90).next().forward(50).next().right(90).next().forward(109).next().backward(75).next().right(155).next().snapToTarget().next().startShooter().next().fire().next().build();
    }
    
    public Command[] simplePath(){
        return AutoPathBuilder.builder(subsystemManager, commandFactory, false).next().zeroYaw().next().hoodHoming().next().snapToTargetStartShooter().next().delayForSeconds(0.5).next().fire().delayForSeconds(3).next().backward(45).next().build();
    }

    public Command[] leftEightBallAuto(){
        //85 -> 137
        return AutoPathBuilder.builder(subsystemManager, commandFactory, true).zeroYaw().startIntake().next().startShooterAndHomeHood().forward(140).next().enableAutoShooterHoodAdjustment().right(155).next().snapToTarget().next().fire().delayForSeconds(2).next().nonRelativeTurn(180).stopShooter().next().forward(85).next().backward(85).startShooter().next().right(155).enableAutoShooterHoodAdjustment().next().snapToTarget().next().fire().next().build();
    }
    
}
