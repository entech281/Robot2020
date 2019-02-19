/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.path;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.CommandGroupFactory;
import frc.robot.commands.DelayCommand;
import frc.robot.commands.DriveForwardSetDistance;
import frc.robot.commands.SnapAndShootCommand;
import frc.robot.commands.SnapToVisionTargetCommand;
import frc.robot.commands.SnapToYawCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
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

    public static Command turnRight(DriveSubsystem drive, double degrees) {
        return new SnapToYawCommand(drive, degrees, true);
    }

    public static Command turnLeft(DriveSubsystem drive, double degrees) {
        return new SnapToYawCommand(drive, -degrees, true);
    }

    public static Command turnNonRelative(DriveSubsystem drive, double direction){
        return new SnapToYawCommand(drive, direction, false);
    }
    
    public static Command snapToTargetAlign(DriveSubsystem drive){
        return new SnapToVisionTargetCommand(drive);
    }
    
    public static Command snapToTargetShoot(DriveSubsystem drive, ShooterSubsystem shooter){
        return new SnapAndShootCommand(drive, shooter);
    }
    
    public static Command fireBalls(IntakeSubsystem intake){
        return intake.startElevator();
    }
    
    public static Command delay(double seconds){
        return new DelayCommand(seconds);
    }
    
    public static Command zeroNavXAngle(NavXSubsystem navX, boolean inverted){
        return navX.zeroYawOfNavX(inverted);
    }
    
    public static Command enableAutoHoodAdjustment(ShooterSubsystem shooter){
        return shooter.enableAutoShooting();
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

        BasicMoves next();
        
        BasicMoves hoodHoming();
        
        BasicMoves startShooter();
        
        BasicMoves startIntake();
        
        BasicMoves stopShooter();
        
        BasicMoves enableAutoShooterHoodAdjustment();
        
        BasicMoves stopJustSpinningShooterWheel();
        
        BasicMoves startShooterAndHomeHood();
        
        @Override
        public boolean equals(Object obj);

        Command[] build();
    }
    
public static class Builder implements BasicMoves{

        private List<Command> parallelCommands;
        private List<Command> commands;
        private DriveSubsystem drive;
        private NavXSubsystem navXSubsystem;
        private VisionSubsystem visionSubsystem;
        private ShooterSubsystem shooter;
        private IntakeSubsystem intake;
        private CommandGroupFactory commandFactory;
        private boolean invertedStart;

        public Builder(SubsystemManager subsystemManager, CommandGroupFactory commandFactory, boolean inverted){
            parallelCommands = new ArrayList<>();
            commands = new ArrayList<>();
            this.drive = subsystemManager.getDriveSubsystem();
            this.navXSubsystem = subsystemManager.getNavXSubsystem();
            this.visionSubsystem = subsystemManager.getVisionSubsystem();
            this.shooter = subsystemManager.getShooterSubsystem();
            this.intake = subsystemManager.getIntakeSubsystem();
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
            parallelCommands.add(turnRight(drive, degrees).withTimeout(10));
            return this;
        }
        
        

        @Override
        public BasicMoves left(double degrees) {
            parallelCommands.add(turnLeft(drive, degrees).withTimeout(10));
            return this;
        }

        @Override
        public BasicMoves forward(double inches) {
            parallelCommands.add(goForward(drive, inches).withTimeout(10));
            return this;
        }

        @Override
        public BasicMoves backward(double inches) {
            parallelCommands.add(goBackward(drive, inches).withTimeout(10));
            return this;
        }
        
        @Override
        public BasicMoves nonRelativeTurn(double direction){
            parallelCommands.add(turnNonRelative(drive, direction).withTimeout(10));
            return this;
        }

        @Override
        public BasicMoves snapToTarget() {
            parallelCommands.add(snapToTargetAlign(drive).withTimeout(10));
            return this;
        }

        @Override
        public BasicMoves snapToTargetStartShooter() {
            parallelCommands.add(commandFactory.getSnapToGoalAndStartShooter().withTimeout(10));
            return this;
            
        }

        @Override
        public BasicMoves fire() {
            parallelCommands.add(fireBalls(intake).withTimeout(10));
            return this;
        }

        @Override
        public BasicMoves zeroYaw() {
            parallelCommands.add(zeroNavXAngle(navXSubsystem, invertedStart).withTimeout(10));
            return this;
        }

        public BasicMoves stopJustSpinningShooterWheel(){
            parallelCommands.add(shooter.turnOffShooter());
            return this;
        }
        
        @Override
        public BasicMoves delayForSeconds(double seconds) {
            parallelCommands.add(delay(seconds).withTimeout(10));
            return this;
        }

        @Override
        public BasicMoves next() {
            Command[] parallelComm = new Command[parallelCommands.size()];
            for(int i = parallelComm.length - 1; i >= 0; i-= 1){
                parallelComm[i] = parallelCommands.remove(i);
            }
            commands.add(new ParallelCommandGroup(parallelComm));
            return this;
        }

        @Override
        public BasicMoves hoodHoming() {
            parallelCommands.add(commandFactory.getHoodHomingCommandGroup());
            return this;
        }

        @Override
        public BasicMoves startShooter() {
            parallelCommands.add(commandFactory.getStartShooterCommandGroup());
            return this;
        }

        @Override
        public BasicMoves startIntake() {
            parallelCommands.add(commandFactory.getStartIntakeCommandGroup());
            return this;
        }

        @Override
        public BasicMoves stopShooter() {
            parallelCommands.add(commandFactory.getStopShooterCommandGroup());
            return this;
        }

        @Override
        public BasicMoves enableAutoShooterHoodAdjustment() {
            parallelCommands.add(enableAutoHoodAdjustment(shooter));
            return this;
        }

        @Override
        public BasicMoves startShooterAndHomeHood() {
            parallelCommands.add(commandFactory.hoodHomeAndStartShooter());
            return this;
        }

    }

}    
