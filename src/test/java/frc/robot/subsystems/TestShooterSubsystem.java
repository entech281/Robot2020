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

        PoseSource mockPoses = Mockito.mock(PoseSource.class);
        ShooterSubsystem2 shooter = new ShooterSubsystem2();
        shooter.setPoseSource(mockPoses);
        
        when(mockPoses.getRobotPose()).thenReturn(
            new RobotPose(
                new RobotPosition(0.0,0.0,0.0),
                VisionData.DEFAULT_VISION_DATA
            ) 
        );
        assertEquals(5300, shooter.getRPMSpeed(), 0.1);
        
        Command c = new RunWhenDisabledCommandDecorator ( new SequentialCommandGroup(
                new PrintCommand ("Starting "),
                new InstantCommand(shooter::decreaseRPMSpeed),
                new PrintCommand ("Done")
        ));
        scheduler.schedule(c);

        for ( int i=0;i<5;i++){
             scheduler.run();
        }
        assertEquals(5150, shooter.getRPMSpeed(), 0.1);
    }
   
}
