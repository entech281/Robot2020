/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/**
 *
 * @author aryan
 */
public class StartIntakeCommand extends ParallelCommandGroup{
    public StartIntakeCommand(IntakeSubsystem intake, ElevatorSubsystem elevator, ShooterSubsystem shoot){
        addCommands(intake.start(), elevator.start(), shoot.stop());
    }
}
