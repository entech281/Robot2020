package frc.robot.commands;

import java.lang.module.ModuleDescriptor.Requires;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BaseSubsystem;

public class BaseCommand extends CommandBase{

    public static final double defaultTimeout = 33;

    public BaseCommand(BaseSubsystem sub){
        this(sub, defaultTimeout);
    }

    public BaseCommand(BaseSubsystem subsystem, double timeout){
        if(subsystem != null){
            addRequirements(subsystem);
        }
        withTimeout(timeout);
    }
}