/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.SubsystemManager;

/**
 *
 * @author aryan
 */
public class CommandGroupFactory {
    
    private SubsystemManager subsystemManager;
    private SequentialCommandGroup hoodHomingCommandGroup;
    private SequentialCommandGroup startShooterCommandGroup;
    
    public CommandGroupFactory(SubsystemManager subsystemManager){
        this.subsystemManager = subsystemManager;
        createCommands();

    }

    private void createCommands(){
//        hoodHomingCommandGroup = new EntechCommandGroup()
//                .addCommand(subsystemManager.getShooterSubsystem().goToUpperLimit())
//                .addCommand(subsystemManager.getShooterSubsystem().returnToStartPos())
//                .getSequentialCommandGroup();

        startShooterCommandGroup = new EntechCommandGroup()
                .addCommand(subsystemManager.getShooterSubsystem().turnOnShooter())
                .getSequentialCommandGroup();




    }
    
    public SequentialCommandGroup getClimbCommandGroup(){
        return new EntechCommandGroup()
                .addCommand(subsystemManager.getClimbSubsystem().dropHookRaisingMech())
                .addCommand(new DelayCommand(0.5))
                .addCommand(subsystemManager.getClimbSubsystem().pullRobotUp())
                .getSequentialCommandGroup();
    }

    public SequentialCommandGroup getStopIntakeCommandGroup(){
        return new EntechCommandGroup()
                .addCommand(subsystemManager.getIntakeSubsystem().stopIntake())
                .addCommand(subsystemManager.getIntakeSubsystem().stopElevator())
                .getSequentialCommandGroup();
    }

    public SequentialCommandGroup getStopShooterCommandGroup(){
        return new EntechCommandGroup()
                .addCommand(subsystemManager.getIntakeSubsystem().stopElevator())
                .addCommand(subsystemManager.getShooterSubsystem().turnOffShooter())
                .addCommand(new HoodHomingCommand(subsystemManager.getShooterSubsystem()))
                .getSequentialCommandGroup();
    }
    
//    public SequentialCommandGroup getStartIntakeCommandGroup(){
//        return new EntechCommandGroup()
//                .addCommand(subsystemManager.getShooterSubsystem().turnOffShooter())
//                .addCommand(subsystemManager.getIntakeSubsystem().startIntake())
//                .addCommand(new IntakeOnCommand(subsystemManager.getIntakeSubsystem()))
//                .getSequentialCommandGroup();
//    }
    
    public SequentialCommandGroup getSnapToGoalAndStartShooter(){
        return new EntechCommandGroup()
                .addCommand(subsystemManager.getShooterSubsystem().enableAutoShooting())
                .addCommand(startShooterCommandGroup)
                .addCommand(new SnapToVisionTargetCommand(subsystemManager.getDriveSubsystem()))
                .getSequentialCommandGroup();
    }
    
    public SequentialCommandGroup getStartShooterCommandGroup(){
        return new EntechCommandGroup()
                .addCommand(getStopIntakeCommandGroup())
                .addCommand(subsystemManager.getIntakeSubsystem().shiftElevatorBack())
                .addCommand(subsystemManager.getShooterSubsystem().turnOnShooter())
                .getSequentialCommandGroup();
    }
    
    public SequentialCommandGroup turnOffAllSubsystems(){
        return new EntechCommandGroup()
                .addCommand(getStopIntakeCommandGroup())
                .addCommand(subsystemManager.getIntakeSubsystem().stopElevator())
                .addCommand(getStopShooterCommandGroup())
                .getSequentialCommandGroup();
        
    }
    
//    public SequentialCommandGroup getHoodHomingCommandGroup(){
//        return new EntechCommandGroup()
//                .addCommand(subsystemManager.getShooterSubsystem().goToUpperLimit())
//                .addCommand(subsystemManager.getShooterSubsystem().returnToStartPos())
//                .getSequentialCommandGroup();
//    }
    
    public SequentialCommandGroup hoodHomeAndStartShooter(){
        SequentialCommandGroup hoodHomingCommand = new EntechCommandGroup()
                .addCommand(subsystemManager.getShooterSubsystem().goToUpperLimit())
                .addCommand(subsystemManager.getShooterSubsystem().returnToStartPos())
                .getSequentialCommandGroup();
        
        return new EntechCommandGroup()
                .addCommand(subsystemManager.getShooterSubsystem().turnOnShooter())
                .addCommand(hoodHomingCommand)
                .getSequentialCommandGroup();
    }
    
}
