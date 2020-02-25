/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import java.util.ArrayList;

/**
 *
 * @author aryan
 */
public class EntechCommandGroup{
    ArrayList<Command> commands = new ArrayList<>();
    public EntechCommandGroup addCommand(Command comm){
        commands.add(comm);
        return this;
    }
    public SequentialCommandGroup getSequentialCommandGroup(){
        Command[] inputCommands = new Command[commands.size()];
        for (int i = 0; i<inputCommands.length; i++){
            inputCommands[i] = commands.get(i);
        }
        return new SequentialCommandGroup(inputCommands);
    }
    public ParallelCommandGroup getParallelCommandGroup(){
        Command[] inputCommands = new Command[commands.size()];
        for (int i = 0; i<inputCommands.length; i++){
            inputCommands[i] = commands.get(i);
        }
        return new ParallelCommandGroup(inputCommands);
    }
    
}
