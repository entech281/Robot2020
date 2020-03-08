/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import frc.robot.pose.PoseSource;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.utils.ShooterCalculations;

/**
 *
 * @author aryan
 */
public class AutoHoodShooterAdjust extends EntechCommandBase{

    private ShooterSubsystem shooter;
    private HoodSubsystem hood;
    private PoseSource poseSource;
    private int customSpeed = 4500;
    private double customPosition = 1000;
    
    public AutoHoodShooterAdjust(ShooterSubsystem shooter, HoodSubsystem hood, PoseSource poseSource) {
        super(shooter, hood);
        this.poseSource = poseSource;
        this.hood = hood;
        this.shooter = shooter;
    }
    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        double distance = poseSource.getRobotPose().getTargetLocation().getDistanceToTarget();
        
        if(poseSource.getRobotPose().getVisionDataValidity()){
            customSpeed = ShooterCalculations.calculateAutoShooterSpeed(distance);
            customPosition = ShooterCalculations.hoodEncoderPosition(distance);
        }
        shooter.setShooterSpeed(customSpeed);
        hood.setHoodPosition(customPosition);
    }

    @Override
    public boolean isFinished(){
        return !shooter.isShooterOn();
    }
}
