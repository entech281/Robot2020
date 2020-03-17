/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.RobotConstants;
import frc.robot.pose.PoseSource;
import frc.robot.pose.RobotPose;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.utils.NavXDataProcessor;
import frc.robot.utils.PIDControlOutputProcessor;

/**
 *
 * @author aryan
 */
public class StopDrivingCommand extends EntechCommandBase{
    private DriveSubsystem drive;

    public StopDrivingCommand(DriveSubsystem drive){
        super(drive);
        this.drive = drive;
    }
    
    @Override
    public void initialize() {
    }

    
    
    
    @Override
    public void execute() {
        drive.drive(0, 0);
        drive.feedWatchDog();
    }

    
}
