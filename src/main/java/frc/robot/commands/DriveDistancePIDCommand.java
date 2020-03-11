/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.RobotConstants;
import frc.robot.utils.EncoderInchesConverter;
import frc.robot.utils.PIDControlOutputProcessor;


/**
 *
 * @author aryan
 */
public class DriveDistancePIDCommand extends EntechCommandBase{
    private DriveSubsystem drive;
    private PIDController controller;
    private double offset;
    private double output;
    private double setpoint;
    private int count = 0;
    private EncoderInchesConverter encoderConverter = new EncoderInchesConverter(1 / RobotConstants.DIMENSIONS.MOTOR_REVOLUTIONS_PER_INCH);

    public DriveDistancePIDCommand(DriveSubsystem drive, double distance){
        super(drive);
        this.drive = drive;
        setpoint = encoderConverter.toCounts(distance);
        this.controller = new PIDController(RobotConstants.PID.AUTO_STRAIGHT_SPEED.P,
            RobotConstants.PID.AUTO_STRAIGHT_SPEED.I,
            RobotConstants.PID.AUTO_STRAIGHT_SPEED.D);        
    }
    
    @Override
    public void initialize() {
        drive.setSpeedMode();
        drive.resetPosition();
        controller.setSetpoint(setpoint);
        controller.setTolerance(encoderConverter.toCounts(1));
    }
    @Override
    public void execute() {
        output = controller.calculate(drive.getDistanceTravelled());
        output =  PIDControlOutputProcessor.constrainWithMinBounds(output, 0.5, 1);
        drive.drive(output, 0);
        drive.feedWatchDog();
    }

    
    @Override
    public boolean isFinished() {
        if(controller.atSetpoint()){
            count += 1;
        } else {
            count = 0;
        }
        return count > 3;
    }
    
}
