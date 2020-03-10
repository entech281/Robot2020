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
import frc.robot.utils.PIDControlOutputProcessor;

/**
 *
 * @author aryan
 */
public class SnapToVisionTargetCommand extends EntechCommandBase {

    private DriveSubsystem drive;
    private PIDController controller;
    private double offset;
    private double output = 0.0;
    private PoseSource poseSource;
    public static final double TIMEOUT_SECONDS=2;

    public SnapToVisionTargetCommand(DriveSubsystem drive, PoseSource poseSource) {
        super(drive,TIMEOUT_SECONDS);
        this.drive = drive;
        this.poseSource = poseSource;
        this.controller = new PIDController(RobotConstants.PID.TARGET_LOCK.P,
            RobotConstants.PID.TARGET_LOCK.I,
            RobotConstants.PID.TARGET_LOCK.D);
    }
    @Override
    public void initialize(){
        controller.setSetpoint(0);
        controller.setTolerance(2);
    }

    @Override
    public void execute(){
        RobotPose rp = poseSource.getRobotPose();
        logger.log("Vision data", rp.getVisionDataValidity());
        if(rp.getVisionDataValidity()){
            offset = rp.getTargetLateralOffset();
            logger.log("Offset", offset);
            output = controller.calculate(offset);
            output = PIDControlOutputProcessor.constrain(output, 0.4);
            logger.log("Output", output);
            drive.drive(0, output);
        }

    }

    @Override
    public boolean isFinished(){
        return controller.atSetpoint() || !poseSource.getRobotPose().getVisionDataValidity();
    }

}
