package frc.robot.commands;

import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/**
 *
 * @author aryan
 */
public class AdjustHoodBackwardCommand extends EntechCommandBase{

    private HoodSubsystem hood;
    public AdjustHoodBackwardCommand(HoodSubsystem hood){
        super(hood);
        this.hood = hood;
    }

    @Override
    public void execute() {
        hood.adjustHoodBackward();
    }

    @Override
    public void initialize() {
    }
    
    
}