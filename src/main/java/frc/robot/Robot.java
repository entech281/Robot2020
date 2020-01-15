/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import frc.robot.subsystems.BaseSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXIntializer;
import edu.wpi.first.wpilibj.SerialPort;
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
     OperatorInterface oi;
     private AHRS navX;
     private DifferentialDriveOdometry robotPose;

     public void robotInit(){
          oi = new OperatorInterface(this);
          robotDrive = new DriveSubsystem();
          Rotation2d gyroAngle;
		double leftDistanceMeters;
		double rightDistanceMeters;
		Pose2d k = robotPose.update(gyroAngle, leftDistanceMeters, rightDistanceMeters)
          navX = new NavXIntializer(SerialPort.Port.kMXP, 1000).getCalibratedNavX();
          BaseSubsystem.initializeList();
     }


     public void teleopPeriodic(){
          CommandScheduler.getInstance().run();
          robotDrive.drive(oi.getDriveInputX(), oi.getDriveInputY());
     }
  
}
