package frc.robot.commands;

import java.util.List;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.path.Position;
import frc.robot.path.PositionCalculator;


public class FollowPositionPathCommand extends CommandBase{

    private DataLogger logger = DataLoggerFactory.getLoggerFactory().createDataLogger(this.getName());

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FollowPositionPathCommand other = (FollowPositionPathCommand) obj;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        return true;
    }
    public DriveSubsystem driveSubsystem;
    public List<Position> path;
    public FollowPositionPathCommand(DriveSubsystem subsystem, List<Position> path) {
        this.driveSubsystem = subsystem;
        this.path = path;
    }
    public FollowPositionPathCommand(DriveSubsystem subsystem, List<Position> path, double timeoutSeconds) {
        this.driveSubsystem = subsystem;
        this.path = path;
    }    
    public FollowPositionPathCommand mirror() {
        return new FollowPositionPathCommand(this.driveSubsystem,PositionCalculator.mirror(this.path));
    }
    
    @Override
    public void initialize() {
        driveSubsystem.startAutonomous();
        
        for (Position p : path) {
            driveSubsystem.getPositionBuffer().addPosition(p);
        }
    }

    @Override
    public void end(boolean interrupted){
        driveSubsystem.endAutonomous();
    }

    @Override
    public boolean isFinished() {
        return ! driveSubsystem.getPositionBuffer().hasNextPosition() ;
    }
    
}