/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.path;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.CommandGroupFactory;
import frc.robot.commands.DelayCommand;
import frc.robot.commands.DriveForwardSetDistance;
import frc.robot.commands.SnapAndShootCommand;
import frc.robot.commands.SnapToVisionTargetCommand;
import frc.robot.commands.SnapToYawCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.NavXSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SubsystemManager;
import frc.robot.subsystems.VisionSubsystem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aryan
 */
public class AutoPathBuilder {
    
    public static Command goForward(DriveSubsystem drive, double inches) {
        return new DriveForwardSetDistance(drive, inches);
    }
    
    public static Command goBackward(DriveSubsystem drive, double inches) {
        return new DriveForwardSetDistance(drive, -inches);
    }

    public static Command turnRight(DriveSubsystem drive, NavXSubsystem navX, double degrees) {
        return new SnapToYawCommand(navX, drive, degrees, true);
    }

    public static Command turnLeft(DriveSubsystem drive, NavXSubsystem navX, double degrees) {
        return new SnapToYawCommand(navX, drive, -degrees, true);
    }

    public static Command turnNonRelative(DriveSubsystem drive, NavXSubsystem navX, double direction){
        return new SnapToYawCommand(navX, drive, direction, false);
    }
    
    public static Command snapToTargetAlign(DriveSubsystem drive, VisionSubsystem vision){
        return new SnapToVisionTargetCommand(drive, vision);
    }
    
    public static Command snapToTargetShoot(DriveSubsystem drive, VisionSubsystem vision, ShooterSubsystem shooter, ElevatorSubsystem elevator){
        return new SnapAndShootCommand(drive, elevator, vision, shooter);
    }
    
    public static Command fireBalls(ElevatorSubsystem elevator){
        return elevator.start();
    }
    
    public static Command delay(double seconds){
        return new DelayCommand(seconds);
    }
    public static Command zeroNavXAngle(NavXSubsystem navX, boolean inverted){
        return navX.zeroYawOfNavX(inverted);
    }
    
    public static BasicMoves builder(SubsystemManager subsystemManager, CommandGroupFactory commandFactory, boolean invertedStart) {
        return new Builder(subsystemManager, commandFactory, invertedStart);
    }
    
    public interface BasicMoves {
        
        BasicMoves zeroYaw();

        BasicMoves right(double degrees);

        BasicMoves left(double degrees);

        BasicMoves forward(double inches);

        BasicMoves backward(double inches);
        
        BasicMoves nonRelativeTurn(double direction);
        
        BasicMoves snapToTarget();

        BasicMoves snapToTargetStartShooter();
        
        BasicMoves fire();
        
        BasicMoves delayForSeconds(double seconds);

        Command[] build();
    }
    
public static class Builder implements BasicMoves{

        private List<Command> commands;
        private DriveSubsystem drive;
        private NavXSubsystem navXSubsystem;
        private VisionSubsystem visionSubsystem;
        private ElevatorSubsystem elevator;
        private ShooterSubsystem shooter;
        private CommandGroupFactory commandFactory;
        private boolean invertedStart;

        public Builder(SubsystemManager subsystemManager, CommandGroupFactory commandFactory, boolean inverted){
            commands = new ArrayList<>();
            this.drive = subsystemManager.getDriveSubsystem();
            this.navXSubsystem = subsystemManager.getNavXSubsystem();
            this.visionSubsystem = subsystemManager.getVisionSubsystem();
            this.elevator = subsystemManager.getElevatorSubsystem();
            this.shooter = subsystemManager.getShooterSubsystem();
            this.commandFactory = commandFactory;
            invertedStart = inverted;
        }
        @Override
        public Command[] build() {
            Command[] sequence = new Command[commands.size()];
            for(int i=0; i < sequence.length; i++){
                sequence[i] = commands.get(i);
            }
            return sequence;
        }

        @Override
        public BasicMoves right(double degrees) {
            commands.add(turnRight(drive, navXSubsystem, degrees).withTimeout(10));
            return this;
        }
        
        

        @Override
        public BasicMoves left(double degrees) {
            commands.add(turnLeft(drive, navXSubsystem, degrees).withTimeout(10));
            return this;
        }

        @Override
        public BasicMoves forward(double inches) {
            commands.add(goForward(drive, inches).withTimeout(10));
            return this;
        }

        @Override
        public BasicMoves backward(double inches) {
            commands.add(goBackward(drive, inches).withTimeout(10));
            return this;
        }
        
        @Override
        public BasicMoves nonRelativeTurn(double direction){
            commands.add(turnNonRelative(drive, navXSubsystem, direction).withTimeout(10));
            return this;
        }

        @Override
        public BasicMoves snapToTarget() {
            commands.add(snapToTargetAlign(drive, visionSubsystem).withTimeout(10));
            return this;
        }

        @Override
        public BasicMoves snapToTargetStartShooter() {
            commands.add(commandFactory.getSnapToGoalAndStartShooter().withTimeout(10));
            return this;
            
        }

        @Override
        public BasicMoves fire() {
            commands.add(fireBalls(elevator).withTimeout(10));
            return this;
        }

        @Override
        public BasicMoves zeroYaw() {
            commands.add(zeroNavXAngle(navXSubsystem, invertedStart).withTimeout(10));
            return this;
        }

        @Override
        public BasicMoves delayForSeconds(double seconds) {
            commands.add(delay(seconds).withTimeout(10));
            return this;
        }

    }

}    
