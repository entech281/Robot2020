/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import frc.robot.path.Position;
import frc.robot.path.PositionCalculator;
import frc.robot.pose.EncoderValues;
import frc.robot.pose.PoseSource;
import frc.robot.pose.RobotPose;
import frc.robot.pose.RobotPosition;
import frc.robot.subsystems.DriveSubsystem;

/**
 *
 * @author dcowden
 */
public class DriveToPositionCommand extends EntechCommandBase{

    public static final double TOLERANCE_INCHES = 0.5;
    
    private DriveSubsystem drive;
    private Position targetPosition;
    private Position currentPosition;
    private double toleranceInches;
    
    public DriveToPositionCommand(DriveSubsystem drive,  Position targetPosition ){
        super(drive);
        this.drive = drive;
        this.targetPosition = targetPosition;
    }
    public DriveToPositionCommand(DriveSubsystem drive, double distanceInches){
        super(drive);
        this.drive = drive;
        this.targetPosition = PositionCalculator.goForward(distanceInches);
    }
    
   @Override
    public void initialize(){
        drive.driveToPosition(targetPosition);
    }
    
    @Override
    public void execute() {
        currentPosition = drive.getCurrentPosition() ;
        logger.log("Current Position", currentPosition);
        logger.log("Target Position", targetPosition);
        drive.feedWatchDog();
    }
    
    @Override
    public boolean isFinished(){
        return currentPosition.isCloseTo(targetPosition, TOLERANCE_INCHES);
    }

    
}
