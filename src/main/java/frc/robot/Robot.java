/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.BaseSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

     DriveSubsystem robotDrive;
     IntakeSubsystem intake;
     OperatorInterface oi;

     public void robotInit(){
          intake = new IntakeSubsystem();
          robotDrive = new DriveSubsystem();
          BaseSubsystem.initializeList();
          oi = new OperatorInterface(this);
     }


     public void teleopPeriodic(){
          CommandScheduler.getInstance().run();
          robotDrive.drive(oi.getDriveInputX(), oi.getDriveInputY());
     }

     public IntakeSubsystem getIntakeSubsystem(){
          return intake;
     }
  
}
