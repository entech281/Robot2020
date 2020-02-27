/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.IntakeSubsystem;

/**
 *
 * @author aryan
 */
public class StopIntakeCommand extends ParallelCommandGroup{
    public StopIntakeCommand(IntakeSubsystem intake){
        addCommands(intake.stopElevator(), intake.stopIntake());
    }
}
