/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import frc.robot.subsystems.ShooterSubsystem;

/**
 *
 * @author aryan
 */
public class AdjustRaiseHoodCommand extends EntechCommandBase{

    private ShooterSubsystem shooter;
    public AdjustRaiseHoodCommand(ShooterSubsystem shooter){
        super(shooter);
        this.shooter = shooter;
    }

    @Override
    public void execute() {
        shooter.adjustHoodForward();
    }

    @Override
    public void initialize() {
    }
    
    
}
