/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;

/**
 *
 * @author aryan
 */
public class SnapAndShootCommand extends ParallelCommandGroup{
    public SnapAndShootCommand(DriveSubsystem drive, ShooterSubsystem shoot){
        addCommands(shoot.enableAutoShooting() , new StartShooterCommand(shoot), new SnapToVisionTargetCommand(drive));        
    }
}
