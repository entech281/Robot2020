
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import frc.robot.subsystems.HoodSubsystem;

/**
 *
 * @author aryan
 */
public class AdjustRaiseHoodCommand extends EntechCommandBase{

    private HoodSubsystem hood;
    public AdjustRaiseHoodCommand(HoodSubsystem hood){
        super(hood);
        this.hood = hood;
    }

    @Override
    public void execute() {
        hood.adjustHoodForward();
    }

    @Override
    public void initialize() {
    }
    
    
}