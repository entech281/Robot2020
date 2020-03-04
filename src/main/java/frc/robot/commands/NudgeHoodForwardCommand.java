package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ShooterSubsystem;

public class NudgeHoodForwardCommand extends EntechCommandBase{
    
    ShooterSubsystem shooter;
    private boolean initializationCompleted;
    public NudgeHoodForwardCommand(ShooterSubsystem shooter){
        super(shooter);
        this.shooter = shooter;
        initializationCompleted = false;
    }

    @Override
    public boolean isFinished() {
        return initializationCompleted;
    }

    @Override
    public void execute() {
        shooter.setHoodMotorSpeed(0.1);
    
    }

    
}
