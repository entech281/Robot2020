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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

    private double maxSpeed = 0;
    public DriveDistancePIDCommand(DriveSubsystem drive, double distance){
        this(drive, distance, 1);
    }

    public DriveDistancePIDCommand(DriveSubsystem drive, double distance, double maxSpeed){
        super(drive);
        this.drive = drive;
        setpoint = distance;
        this.controller = new PIDController(RobotConstants.PID.AUTO_STRAIGHT_SPEED.P,
            RobotConstants.PID.AUTO_STRAIGHT_SPEED.I,
            RobotConstants.PID.AUTO_STRAIGHT_SPEED.D);        
        this.maxSpeed = maxSpeed;
    }

    
    @Override
    public void initialize() {
        drive.switchToBrakeMode();
        drive.resetPosition();
        controller.setSetpoint(setpoint);
        controller.setTolerance(1);
    }
    @Override
    public void execute() {
        output = controller.calculate(drive.getDistanceTravelled())/10;
        SmartDashboard.putNumber("Output", output);
        SmartDashboard.putNumber("Position", drive.getDistanceTravelled());
        output =  PIDControlOutputProcessor.constrainWithMinBounds(output, maxSpeed, 0.3);
        drive.drive(output, 0);
        drive.feedWatchDog();
    }

    
    @Override
    public boolean isFinished() {
         if(controller.atSetpoint()){
            drive.drive(0, 0);
        }
        return controller.atSetpoint();
    }
    
}
