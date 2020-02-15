package frc.robot.commands;

import java.util.List;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.path.Position;
import frc.robot.path.PositionCalculator;


public class FollowPositionPathCommand extends CommandBase{
    int counter = 0;
    private DataLogger logger = DataLoggerFactory.getLoggerFactory().createDataLogger(this.getName());


    public DriveSubsystem driveSubsystem;
    public List<Position> path;
    public FollowPositionPathCommand(DriveSubsystem subsystem, List<Position> path) {
        this.driveSubsystem = subsystem;
        this.path = path;
        this.addRequirements(subsystem);
    }
    public FollowPositionPathCommand(DriveSubsystem subsystem, List<Position> path, double timeoutSeconds) {
        this.driveSubsystem = subsystem;
        this.path = path;
        this.addRequirements(subsystem);
    }    
    public FollowPositionPathCommand mirror() {
        return new FollowPositionPathCommand(this.driveSubsystem,PositionCalculator.mirror(this.path));
    }
    
    @Override
    public void initialize() {
        driveSubsystem.startAutonomous();
        logger.log("Is Running", true);
        for (Position p : path) {
            driveSubsystem.getPositionBuffer().addPosition(p);
        }
    }

    @Override
    public void end(boolean interrupted){
        driveSubsystem.endAutonomous();
        logger.log("Is Running", false);
    }

    @Override
    public boolean isFinished() {
        ++counter;
        logger.log("Times Ran", counter);
        return ! driveSubsystem.getPositionBuffer().hasNextPosition() ;
    }
    
}