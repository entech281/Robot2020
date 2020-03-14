/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.utils.RunWhenDisabledCommandDecorator;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

/**
 *
 * @author dcowden
 */
public class TestSimulateDrivingCommand {
    
    protected CommandScheduler scheduler= CommandScheduler.getInstance();
    
   

    protected void runScheduleSteps( int timesToRun){
        for(int i=0;i<timesToRun;i++){
            scheduler.run();
        }        
    }
    protected void scheduleTestComand(Command commandToRun){
        scheduler.schedule( new RunWhenDisabledCommandDecorator(commandToRun) );
    }
    
    @Test
    public void testDrivingCommand() throws Exception{
        DriveSubsystem fakeDrive = Mockito.mock(DriveSubsystem.class);
        
        int TIMES_EXPECTED=4;
        Command simulateDrive = new SimulateDrivingCommand(fakeDrive,0.2,1.0,0);

        scheduleTestComand(simulateDrive);
        runScheduleSteps(TIMES_EXPECTED);

        verify(fakeDrive, times(TIMES_EXPECTED)).drive(1.0, 0);

    }
}
