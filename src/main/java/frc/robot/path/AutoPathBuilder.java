/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.path;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.DriveForwardSetDistance;
import frc.robot.commands.SnapToVisionTargetCommand;
import frc.robot.commands.SnapToYawCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubsystem;
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
    
    public static BasicMoves builder(DriveSubsystem drive, NavXSubsystem navX, VisionSubsystem vision) {
        return new Builder(drive, vision, navX);
    }
    
    public interface BasicMoves {

        BasicMoves right(double degrees);

        BasicMoves left(double degrees);

        BasicMoves forward(double inches);

        BasicMoves backward(double inches);
        
        BasicMoves nonRelativeTurn(double direction);
        
        BasicMoves snapToTarget();

        Command[] build();
    }
    
        public static class Builder implements BasicMoves{

        private List<Command> commands = new ArrayList<>();
        private DriveSubsystem drive = new DriveSubsystem();
        private NavXSubsystem navXSubsystem = new NavXSubsystem();
        private VisionSubsystem visionSubsystem = new VisionSubsystem();

        public Builder(DriveSubsystem drive, VisionSubsystem vision, NavXSubsystem navX){
            this.drive = drive;
            this.navXSubsystem = navX;
            this.visionSubsystem = vision;
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
            commands.add(turnRight(drive, navXSubsystem, degrees));
            return this;
        }
        
        

        @Override
        public BasicMoves left(double degrees) {
            commands.add(turnLeft(drive, navXSubsystem, degrees));
            return this;
        }

        @Override
        public BasicMoves forward(double inches) {
            commands.add(goForward(drive, inches));
            return this;
        }

        @Override
        public BasicMoves backward(double inches) {
            commands.add(goBackward(drive, inches));
            return this;
        }
        
        @Override
        public BasicMoves nonRelativeTurn(double direction){
            commands.add(turnNonRelative(drive, navXSubsystem, direction));
            return this;
        }

        @Override
        public BasicMoves snapToTarget() {
            commands.add(snapToTargetAlign(drive, visionSubsystem));
            return this;
        }

    }

}    
