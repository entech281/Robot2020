/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import frc.robot.path.Position;
import frc.robot.path.PositionCalculator;
import frc.robot.subsystems.DriveSubsystem;

/**
 *
 * @author aryan
 */
public class DriveForwardSetDistance extends EntechCommandBase {
    
    private DriveSubsystem drive;
    Position pose;

    public DriveForwardSetDistance(DriveSubsystem drive, double distance){
        super(drive);
        this.drive = drive;
        pose = PositionCalculator.goForward(distance);
    }

    @Override
    public boolean isFinished() {
        return !drive.getPositionBuffer().hasNextPosition();
    }

    @Override
    public void execute() {
        drive.getAutoController().periodic();
    }

    @Override
    public void initialize() {
        drive.startAutonomous();
        drive.driveToPosition(pose);
    }
    
    
    
}
