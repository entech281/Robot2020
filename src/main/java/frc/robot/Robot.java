/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.pose.PoseManager;
import frc.robot.subsystems.BaseSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.NavXSubsystem;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {


     private DataLogger logger;
     DriveSubsystem robotDrive;
     IntakeSubsystem intake;
     OperatorInterface oi;
     PoseManager officialPose;
     NavXSubsystem navX;

     public void robotInit(){
          officialPose = new PoseManager(robotDrive.getEncoderPoseGenerator());
          officialPose.configureRobotPose(0, 0, 90);
          
          intake = new IntakeSubsystem();
          robotDrive = new DriveSubsystem();
          navX = new NavXSubsystem();
          BaseSubsystem.initializeList();
          robotDrive.reset();          
          oi = new OperatorInterface(this);
          
          this.logger = DataLoggerFactory.getLoggerFactory().createDataLogger("Robot Main Loop");
     }

     @Override
     public void teleopPeriodic(){
          CommandScheduler.getInstance().run();
          robotDrive.drive(oi.getNextInstruction());
          robotDrive.updatePose(officialPose.getPose());
          logger.log("Current Pose",officialPose.getPose());
     }

     @Override
     public void disabledPeriodic(){
          CommandScheduler.getInstance().run();
     }



     public IntakeSubsystem getIntakeSubsystem(){
          return intake;
     }

     public NavXSubsystem getNavXSubsystem(){
          return navX;
     }

     public DriveSubsystem getDriveSubsystem(){
          return robotDrive;
     }

     public PoseManager getOfficialPose(){
          return officialPose;
     }
  
}
