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
    private ParallelCommandGroup snapToTargetAndStartShooter;
    private ParallelCommandGroup startIntakeCommandGroup;
    private SequentialCommandGroup stopShooterCommandGroup;
    private SequentialCommandGroup stopIntakeCommandGroup;
    private SequentialCommandGroup climbCommandGroup;
    
    public CommandGroupFactory(SubsystemManager subsystemManager){
        this.subsystemManager = subsystemManager;
        createCommands();

    }

    private void createCommands(){
        hoodHomingCommandGroup = new EntechCommandGroup()
                .addCommand(subsystemManager.getShooterSubsystem().goToUpperLimit())
                .addCommand(subsystemManager.getShooterSubsystem().returnToStartPos())
                .getSequentialCommandGroup();

        startShooterCommandGroup = new EntechCommandGroup()
                .addCommand(subsystemManager.getElevatorSubsystem().shiftBack())
                .addCommand(subsystemManager.getShooterSubsystem().turnOnShooter())
                .getSequentialCommandGroup();

        snapToTargetAndStartShooter = new EntechCommandGroup()
                .addCommand(subsystemManager.getShooterSubsystem().enableAutoShooting())
                .addCommand(startShooterCommandGroup)
                .addCommand(new SnapToVisionTargetCommand(subsystemManager.getDriveSubsystem(), subsystemManager.getVisionSubsystem()))
                .getParallelCommandGroup();

        startIntakeCommandGroup = new EntechCommandGroup()
                .addCommand(subsystemManager.getIntakeSubsystem().start())
                .addCommand(subsystemManager.getElevatorSubsystem().start())
                .addCommand(subsystemManager.getShooterSubsystem().turnOffShooter())
                .getParallelCommandGroup();

        stopShooterCommandGroup = new EntechCommandGroup()
                .addCommand(subsystemManager.getElevatorSubsystem().stop())
                .addCommand(subsystemManager.getShooterSubsystem().turnOffShooter())
                .addCommand(hoodHomingCommandGroup)
                .getSequentialCommandGroup();

        stopIntakeCommandGroup = new EntechCommandGroup()
                .addCommand(subsystemManager.getIntakeSubsystem().stop())
                .addCommand(subsystemManager.getElevatorSubsystem().stop())
                .getSequentialCommandGroup();

        climbCommandGroup = new EntechCommandGroup()
                .addCommand(subsystemManager.getClimbSubsystem().dropHookRaisingMech())
                .addCommand(new DelayCommand(0.5))
                .addCommand(subsystemManager.getClimbSubsystem().pullRobotUp())
                .getSequentialCommandGroup();
    }
    
    public SequentialCommandGroup getClimbCommandGroup(){
        return climbCommandGroup;
    }

    public SequentialCommandGroup getStopIntakeCommandGroup(){
        return stopIntakeCommandGroup;
    }

    public SequentialCommandGroup getStopShooterCommandGroup(){
        return stopShooterCommandGroup;
    }
    
    public ParallelCommandGroup getStartIntakeCommandGroup(){
        return startIntakeCommandGroup;
    }
    
    public ParallelCommandGroup getSnapToGoalAndStartShooter(){
        return snapToTargetAndStartShooter;
    }
    
    public SequentialCommandGroup getStartShooterCommandGroup(){
        return startShooterCommandGroup;
    }
    
    public SequentialCommandGroup getHoodHomingCommandGroup(){
        return hoodHomingCommandGroup;
    }
    
}
