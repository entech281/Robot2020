/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HoodSubsystem;

/**
 *
 * @author dcowden
 */
public class HoodAutoAdjustCommand extends CommandBase{
    
    private VisionSubsystem vision;
    private HoodSubsystem hood;
    private VisionDataProcessor processor = new VisionDataProcessor();
    public HoodAutoAdjustCommand(VisionSubsystem vision, HoodSubsystem hood){
        this.hood = hood;
        this.vision = vision;
    }
    
    public void execute(){
        VisionData vd = vision.getVisionData();
        if(rPose.getVisionDataValidity()){
            ShooterConfiguration config = processor.calculateShooterConfiguration(rPose.getTargetLocation());
            setDesiredShooterConfiguration(config);                    
        }                
    }
}
