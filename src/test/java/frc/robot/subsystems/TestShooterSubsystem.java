/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.pose.PoseSource;
import frc.robot.pose.RobotPose;
import frc.robot.pose.RobotPosition;
import frc.robot.pose.VisionData;
import frc.robot.utils.RunWhenDisabledCommandDecorator;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public class TestShooterSubsystem {
 
    protected CommandScheduler scheduler= CommandScheduler.getInstance();
    @Test
    public void testShooterSubsystemStartup(){

        ShooterSubsystem2 shooter = Mockito.mock(ShooterSubsystem2.class);
 
        Command c = new RunWhenDisabledCommandDecorator ( new SequentialCommandGroup(
                new PrintCommand ("Starting "),
                new InstantCommand(shooter::decreaseRPMSpeed),
                new PrintCommand ("Done")
        ));
        Command d = new RunWhenDisabledCommandDecorator ( 
                new InstantCommand ( () -> shooter.adjustHoodPosition(10.0))
        );

        scheduler.schedule(c,d);

        for ( int i=0;i<5;i++){
             scheduler.run();
        }
        verify(shooter).decreaseRPMSpeed();
        verify(shooter).adjustHoodPosition(10.0);
    }
   
}
