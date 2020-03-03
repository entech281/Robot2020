/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aryan
 */
public class AutonomousPathCommand extends SequentialCommandGroup{

    public AutonomousPathCommand(Command[] commands){
        addCommands(commands);
    }

}
