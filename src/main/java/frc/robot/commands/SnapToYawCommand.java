/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.DriveInstruction;
import frc.robot.RobotConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubsystem;
import frc.robot.utils.NavXDataProcessor;
import frc.robot.utils.PIDControlOutputProcessor;

/**
 *
 * @author aryan
 */
public class SnapToYawCommand extends EntechCommandBase{
    private NavXSubsystem navX;
    private DriveSubsystem drive;
    private PIDController controller;
    private double offset;
    private double output;
    private double setpoint;
    private boolean relative;
    private double desiredYaw;

    
    public SnapToYawCommand(NavXSubsystem navXSubsystem, DriveSubsystem drive, double desiredAngle, boolean relative){
        super(drive);
        this.drive = drive;
        this.navX = navXSubsystem;
        this.controller = new PIDController(RobotConstants.PID.AUTO_TURN.P, RobotConstants.PID.AUTO_TURN.I, RobotConstants.PID.AUTO_TURN.D);
        desiredYaw = desiredAngle;
        this.relative = relative;
    }
    
    @Override
    public void initialize() {
        drive.setPositionMode();
        setpoint = desiredYaw;
        if(relative){
            setpoint = navX.updateNavXAngle().getAngle() + setpoint;
            setpoint = NavXDataProcessor.bringInRange(setpoint);
        }
        controller.setSetpoint(setpoint);
        controller.setTolerance(3);
        controller.enableContinuousInput(-180.0, 180.0);
    }

    
    
    
    @Override
    public void execute() {
        output = controller.calculate(navX.updateNavXAngle().getAngle());
        logger.log("Setpoint", controller.getSetpoint());
        logger.log("Offset", controller.getPositionError());
        logger.log("NAV", navX.updateNavXAngle().getAngle());
//        output *= 3;
        output = PIDControlOutputProcessor.constrain(output, 0.6);
        drive.drive(new DriveInstruction(0, output));

    }

    
    @Override
    public boolean isFinished() {
        return controller.atSetpoint();
    }
    
}
