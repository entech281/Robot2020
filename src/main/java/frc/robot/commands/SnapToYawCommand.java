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
public class SnapToYawCommand extends EntechCommandBase{
    private DriveSubsystem drive;
    private PIDController controller;
    private double offset;
    private double output;
    private double setpoint;
    private boolean relative;
    private double desiredYaw;
    private PoseSource poseSource;
    
    
    public SnapToYawCommand(DriveSubsystem drive, double desiredAngle, boolean relative, PoseSource poseSource ){
        super(drive);
        this.drive = drive;
        this.controller = new PIDController(RobotConstants.PID.AUTO_TURN.P, RobotConstants.PID.AUTO_TURN.I, RobotConstants.PID.AUTO_TURN.D);
        desiredYaw = desiredAngle;
        this.relative = relative;
        this.poseSource = poseSource;
    }
    
    @Override
    public void initialize() {
        drive.setPositionMode();
        setpoint = desiredYaw;
        if(relative){
            setpoint = poseSource.getRobotPose().getRobotPosition().getTheta() + setpoint;
            setpoint = NavXDataProcessor.bringInRange(setpoint);
        }
        controller.setSetpoint(setpoint);
        controller.setTolerance(0.5);
        controller.enableContinuousInput(-180.0, 180.0);
    }

    
    
    
    @Override
    public void execute() {
        RobotPose rPose = poseSource.getRobotPose();
        output = controller.calculate(rPose.getRobotPosition().getTheta());
        logger.log("Setpoint", controller.getSetpoint());
        logger.log("Offset", controller.getPositionError());
        logger.log("NAV", rPose.getRobotPosition().getTheta());
        output = PIDControlOutputProcessor.constrain(output, 0.6);
        drive.drive(0, output);

    }

    
    @Override
    public boolean isFinished() {
        return controller.atSetpoint();
    }
    
}
