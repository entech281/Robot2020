/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ShooterSubsystem;

/**
 *
 * @author aryan
 */
public class StopShooterCommand extends SequentialCommandGroup  {
    public StopShooterCommand(ShooterSubsystem shoot) {
        addCommands(new HoodHomingCommand(shoot), shoot.turnOffShooter());
    }
}
