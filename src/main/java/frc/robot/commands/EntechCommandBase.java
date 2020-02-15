package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BaseSubsystem;

public class EntechCommandBase extends CommandBase{

    public static final double DEFAULT_TIMEOUT_SECONDS = 33;

    public EntechCommandBase(BaseSubsystem sub){
        this(sub, DEFAULT_TIMEOUT_SECONDS);
    }

    public EntechCommandBase(BaseSubsystem subsystem, double timeout){
        addRequirements(subsystem);
        withTimeout(timeout);
    }
}